package com.third.games.common.service.impl;

import com.third.games.common.config.EmailProperties;
import com.third.games.common.config.SmsProperties;
import com.third.games.common.enums.VerifyCodeTypeEnum;
import com.third.games.common.events.EmailSendEvent;
import com.third.games.common.events.SmsSendEvent;
import com.third.games.common.exception.BizException;
import com.third.games.common.result.Result;
import com.third.games.common.service.VerifyCodeService;
import com.third.games.common.utils.AESUtil;
import com.third.games.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {
    private static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    private SmsProperties smsProperties;
    @Autowired
    private EmailProperties emailProperties;
    @Autowired
    private RedisUtil redisUtils;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public Result<String> send(Integer templateId, String title, String content, String countryCode, String account, String ip, VerifyCodeTypeEnum type) throws BizException {
        if (type == VerifyCodeTypeEnum.PHONE) {
            return sendSms(templateId, title, content, countryCode, account, ip);
        } else if (type == VerifyCodeTypeEnum.EMAIL) {
            return sendEmail(title, content, account, ip);
        } else {
            throw new BizException(0, "不支持的验证码类型");
        }
    }

    private Result<String> sendSms(Integer templateId, String title, String content, String countryCode, String phone, String ip) throws BizException {
        try {
            String redisKey = getSmsRedisKey(phone);
            // 检查每天发送次数是否超限
            Integer dailyCount = Integer.parseInt(String.valueOf(redisUtils.get(redisKey + ":dailyCount")));
            if (dailyCount >= smsProperties.getLimit().getMaxPerDay()) {
                throw new BizException(0, "每日发送次数已达上限，请明天再试");
            }

            // 检查最小间隔时间
            String lastSendTimeStr = String.valueOf(redisUtils.get(redisKey + ":lastSendTime"));
            if (lastSendTimeStr != null) {
                long lastSendTime = Long.parseLong(lastSendTimeStr);
                long currentTime = System.currentTimeMillis();
                if ((currentTime - lastSendTime) < smsProperties.getLimit().getIntervalSeconds() * 1000L) {
                    throw new BizException(0, "发送过于频繁，请稍后再试");
                }
            }

            String code = generateCode(6);
            String value = AESUtil.encrypt(code, "fZ7#Qp@L9s1!eR2B");
            publisher.publishEvent(new SmsSendEvent(templateId, title, content, countryCode, phone, ip));
            redisUtils.set(verifyCodeKey(code), value, 15 * 60);
            // 更新 Redis 中的发送次数和发送时间
            redisUtils.increment(redisKey + ":dailyCount", 1);
            redisUtils.set(redisKey + ":lastSendTime", String.valueOf(System.currentTimeMillis()), -1);

            // 设置一天后过期，自动清除当天的计数
            redisUtils.expire(redisKey + ":dailyCount", Duration.ofDays(1));
            return Result.success(value);
        } catch (Exception e) {
            throw new BizException(0, e.getMessage());
        }
    }

    private Result<String> sendEmail(String title, String content, String email, String ip) {
        try {
            String redisKey = getEmailRedisKey(email);

            // 检查每天发送次数是否超限
            Integer dailyCount = Integer.parseInt(redisUtils.get(redisKey + ":dailyCount").toString());
            if (dailyCount >= emailProperties.getLimit().getMaxPerDay()) {
                throw new BizException(0, "每日发送次数已达上限，请明天再试");
            }

            // 检查最小间隔时间
            String lastSendTimeStr = String.valueOf(redisUtils.get(redisKey + ":lastSendTime"));
            if (lastSendTimeStr != null) {
                long lastSendTime = Long.parseLong(lastSendTimeStr);
                long currentTime = System.currentTimeMillis();
                if ((currentTime - lastSendTime) < emailProperties.getLimit().getIntervalSeconds() * 1000L) {
                    throw new BizException(0, "发送过于频繁，请稍后再试");
                }
            }
            String code = generateCode(6);
            String value = AESUtil.encrypt(code, "fZ7#Qp@L9s1!eR2B");
            publisher.publishEvent(new EmailSendEvent(title, content, email, ip));
            redisUtils.set(verifyCodeKey(code), value, 15 * 60);
            // 更新 Redis 中的发送次数和发送时间
            redisUtils.increment(redisKey + ":dailyCount", 1);
            redisUtils.set(redisKey + ":lastSendTime", String.valueOf(System.currentTimeMillis()), -1);
            // 设置一天后过期，自动清除当天的计数
            redisUtils.expire(redisKey + ":dailyCount", Duration.ofDays(1));
            publisher.publishEvent(new EmailSendEvent(title, content, email, ip));
            return Result.success(value);
        } catch (Exception e) {
            throw new BizException(0, e.getMessage());
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
}

