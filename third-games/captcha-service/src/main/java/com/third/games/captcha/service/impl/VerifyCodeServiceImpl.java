package com.third.games.captcha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.third.games.captcha.config.EmailProperties;
import com.third.games.captcha.config.SmsProperties;
import com.third.games.captcha.events.EmailSendEvent;
import com.third.games.captcha.events.SmsSendEvent;
import com.third.games.captcha.service.IVerifyCodeService;
import com.third.games.common.bo.VerifyCodeBO;
import com.third.games.common.entity.VerifyCodeLog;
import com.third.games.common.enums.VerifyCodeTypeEnum;
import com.third.games.common.exception.BizException;
import com.third.games.common.mapper.VerifyCodeLogMapper;
import com.third.games.common.result.Result;
import com.third.games.common.utils.AESUtil;
import com.third.games.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * <p>
 * 验证码发送记录表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-05-07
 */
@Service
public class VerifyCodeServiceImpl extends ServiceImpl<VerifyCodeLogMapper, VerifyCodeLog> implements IVerifyCodeService {
    private static final SecureRandom RANDOM = new SecureRandom();
    private final String aesKey = "fZ7#Qp@L9s1!eR2B";

    @Autowired
    private SmsProperties smsProperties;
    @Autowired
    private EmailProperties emailProperties;
    @Autowired
    private RedisUtil redisUtils;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private AESUtil aesUtil;

    @Override
    public Result<String> send(Integer templateId, String title, String content, String countryCode, String account, String ip, VerifyCodeTypeEnum type) throws BizException {
        if (type == VerifyCodeTypeEnum.PHONE) {
            return sendSms(templateId, title, content, countryCode, account, ip);
        } else if (type == VerifyCodeTypeEnum.EMAIL) {
            return sendEmail(title, content, account, ip);
        } else {
            throw new BizException(1, "不支持的验证码类型");
        }
    }

    private Result<String> sendSms(Integer templateId, String title, String content, String countryCode, String phone, String ip) throws BizException {
        try {
            String redisKey = getSmsRedisKey(phone);
            // 检查每天发送次数是否超限
            Object count = redisUtils.get(redisKey + ":dailyCount");
            int dailyCount = 0;
            if (count != null) {
                dailyCount = Integer.parseInt(String.valueOf(count));
            }
            if (dailyCount >= smsProperties.getLimit().getMaxPerDay()) {
                throw new BizException(1, "每日发送次数已达上限，请明天再试");
            }

            // 检查最小间隔时间
            Object temp = redisUtils.get(redisKey + ":lastSendTime");
            String lastSendTimeStr = "";
            if (temp != null) {
                lastSendTimeStr = String.valueOf(temp);
            }
            if (!lastSendTimeStr.isEmpty()) {
                long lastSendTime = Long.parseLong(lastSendTimeStr);
                long currentTime = System.currentTimeMillis();
                if ((currentTime - lastSendTime) < smsProperties.getLimit().getIntervalSeconds() * 1000L) {
                    throw new BizException(1, "发送过于频繁，请稍后再试");
                }
            }

            String code = generateCode(6);
            String value = aesUtil.encrypt(code, aesKey);
            redisUtils.set(verifyCodeKey(code), value, 15 * 60);
            // 更新 Redis 中的发送次数和发送时间
            redisUtils.increment(redisKey + ":dailyCount", 1);
            redisUtils.set(redisKey + ":lastSendTime", String.valueOf(System.currentTimeMillis()), -1);

            // 设置一天后过期，自动清除当天的计数
            redisUtils.expire(redisKey + ":dailyCount", toadyEndDuration());
            publisher.publishEvent(new SmsSendEvent(templateId, title, content, countryCode, phone, ip));
            return Result.success(value);
        } catch (Exception e) {
            throw new BizException(1, e.getMessage());
        }
    }

    private Result<String> sendEmail(String title, String content, String email, String ip) {
        try {
            String redisKey = getEmailRedisKey(email);

            // 检查每天发送次数是否超限
            int dailyCount = Integer.parseInt(redisUtils.get(redisKey + ":dailyCount").toString());
            if (dailyCount >= emailProperties.getLimit().getMaxPerDay()) {
                throw new BizException(1, "每日发送次数已达上限，请明天再试");
            }

            // 检查最小间隔时间
            String lastSendTimeStr = String.valueOf(redisUtils.get(redisKey + ":lastSendTime"));
            if (lastSendTimeStr != null) {
                long lastSendTime = Long.parseLong(lastSendTimeStr);
                long currentTime = System.currentTimeMillis();
                if ((currentTime - lastSendTime) < emailProperties.getLimit().getIntervalSeconds() * 1000L) {
                    throw new BizException(1, "发送过于频繁，请稍后再试");
                }
            }
            String code = generateCode(6);
            String value = aesUtil.encrypt(code, aesKey);
            redisUtils.set(verifyCodeKey(code), value, 15 * 60);
            // 更新 Redis 中的发送次数和发送时间
            redisUtils.increment(redisKey + ":dailyCount", 1);
            redisUtils.set(redisKey + ":lastSendTime", String.valueOf(System.currentTimeMillis()), -1);
            // 设置一天后过期，自动清除当天的计数
            redisUtils.expire(redisKey + ":dailyCount", toadyEndDuration());
            publisher.publishEvent(new EmailSendEvent(title, content, email, ip));
            return Result.success(value);
        } catch (Exception e) {
            throw new BizException(1, e.getMessage());
        }
    }

    private String verifyCodeKey(String code) {
        return String.format("verify_code:%s", code);
    }

    public static String generateCode(int length) {
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            code.append(RANDOM.nextInt(10)); // 0-9
        }
        return String.valueOf(code);
    }

    // 获取 Redis 键值（短信）
    private String getSmsRedisKey(String phone) {
        return "verify:sms:count:" + phone;
    }

    // 获取 Redis 键值（邮件）
    private String getEmailRedisKey(String email) {
        return "verify:email:count:" + email;
    }

    @Override
    public Result<Boolean> verify(VerifyCodeBO request) throws BizException {
        if (request == null || request.getVerifyCode() == null || request.getVerifyCode().isEmpty()) {
            throw new BizException(1, "参数错误");
        }
        Object value = redisUtils.get(verifyCodeKey(request.getVerifyCode()));
        if (value == null) {
            throw new BizException(1, "验证码过期");
        }
        if (!Objects.equals(String.valueOf(value), request.getVerifyId())) {
            throw new BizException(1, "验证码无效");
        }
        redisUtils.del(verifyCodeKey(request.getVerifyCode()));
        return Result.success(true);
    }

    private Duration toadyEndDuration() {
        LocalDateTime endOfDay = LocalDateTime.now().toLocalDate().atTime(23, 59, 59);
        long endOfDayTime = endOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return Duration.ofMillis(endOfDayTime - System.currentTimeMillis());
    }
}
