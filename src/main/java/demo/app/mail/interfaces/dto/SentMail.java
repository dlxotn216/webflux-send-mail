package demo.app.mail.interfaces.dto;

import demo.app.mail.domain.Mail;
import lombok.Getter;

import java.util.List;

/**
 * Created by itaesu on 29/07/2019.
 */
@Getter
public final class SentMail {
    private String id;
    private String subject;
    private String sender;
    private List<String> recipients;
    private String content;

    private SentMail() {
    }

    private SentMail(String id, String subject, String sender, List<String> recipients, String content) {
        this.id = id;
        this.subject = subject;
        this.sender = sender;
        this.recipients = recipients;
        this.content = content;
    }

    public static SentMail from(Mail mail) {
        return new SentMail(mail.getId(), mail.getSubject(), mail.getSender(), mail.getRecipients(), mail.getContent());
    }
}
