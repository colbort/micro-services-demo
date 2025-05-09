package com.third.games.captcha.events;

import lombok.Getter;

@Getter
public class SmsSendEvent {
    private final Integer templateId;
    private final String title;
    private final String content;
    private final String countryCode;
    private final String phone;
    private final String ip;

    public SmsSendEvent(Integer templateId, String title, String content, String countryCode, String phone, String ip) {
        this.templateId = templateId;
        this.title = title;
        this.content = content;
        this.countryCode = countryCode;
        this.phone = phone;
        this.ip = ip;
    }
}
