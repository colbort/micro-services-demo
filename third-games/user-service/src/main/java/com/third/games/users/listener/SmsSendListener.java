package com.third.games.users.listener;

import com.third.games.common.events.SmsSendEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmsSendListener {
    @Async
    @EventListener
    public void onSmsSend(SmsSendEvent event) {
        System.out.printf("接收到SMS发送事件；手机号=%s, 发送标题=%s\n", event.getPhone(), event.getTitle());
    }
}
