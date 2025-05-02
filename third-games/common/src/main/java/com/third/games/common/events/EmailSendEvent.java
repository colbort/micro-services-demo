package com.third.games.common.events;

import lombok.Getter;

@Getter
public class EmailSendEvent {
    private final String title;
    private final String content;
    private final String email;
    private final String ip;

    public EmailSendEvent(String title, String content, String email, String ip) {
        this.title = title;
        this.content = content;
        this.email = email;
        this.ip = ip;
    }
}
