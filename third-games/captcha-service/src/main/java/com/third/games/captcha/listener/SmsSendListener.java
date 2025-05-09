package com.third.games.captcha.listener;

import com.third.games.captcha.events.SmsSendEvent;
import com.third.games.common.entity.VerifyCodeLog;
import com.third.games.common.enums.VerifyCodeTypeEnum;
import com.third.games.common.mapper.VerifyCodeLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class SmsSendListener {
    @Autowired
    private VerifyCodeLogMapper codeLogMapper;

    @Async
    @EventListener
    public void onSmsSend(SmsSendEvent event) {
        System.out.printf("接收到SMS发送事件；手机号=%s, 发送标题=%s\n", event.getPhone(), event.getTitle());

        VerifyCodeLog codeLog = new VerifyCodeLog();
        codeLog.setTitle(event.getTitle());
        codeLog.setContent(event.getContent());
        codeLog.setAccount(event.getPhone());
        codeLog.setSendType(VerifyCodeTypeEnum.PHONE);
        codeLog.setIp(event.getIp());
        codeLog.setStatus(true);
        codeLog.setCreateTime(LocalDateTime.now());

        codeLogMapper.insert(codeLog);
    }
}
