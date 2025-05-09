package com.third.games.captcha.listener;

import com.third.games.captcha.config.EmailProperties;
import com.third.games.captcha.events.EmailSendEvent;
import com.third.games.common.entity.VerifyCodeLog;
import com.third.games.common.enums.VerifyCodeTypeEnum;
import com.third.games.common.mapper.VerifyCodeLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Properties;

@Slf4j
@Component
public class EmailSendListener {
//    @Autowired
//    private JavaMailSender sender;
    @Autowired
    private VerifyCodeLogMapper codeLogMapper;
    @Autowired
    private EmailProperties config;

    @Async
    @EventListener
    public void onEmailSend(EmailSendEvent event) {
        System.out.printf("接收到email发送事件；邮箱账号=%s, 发送标题=%s\n", event.getEmail(), event.getTitle());

        try {
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", config.getHost());
            properties.put("mail.smtp.port", config.getPort()); // SMTP 端口
            properties.put("mail.smtp.auth", config.getAuth());
            properties.put("mail.smtp.starttls.enable", config.getStarttlsEnable()); // 开启 TLS

            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(config.getFrom(), config.getPassword());
                }
            });

            // 创建邮件
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.getFrom()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(event.getEmail()));
            message.setSubject(event.getTitle());
            message.setText(event.getContent());

            // 发送邮件
            Transport.send(message);

            VerifyCodeLog codeLog = new VerifyCodeLog();
            codeLog.setTitle(event.getTitle());
            codeLog.setContent(event.getContent());
            codeLog.setAccount(event.getEmail());
            codeLog.setSendType(VerifyCodeTypeEnum.EMAIL);
            codeLog.setIp(event.getIp());
            codeLog.setStatus(true);
            codeLog.setCreateTime(LocalDateTime.now());

            codeLogMapper.insert(codeLog);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
