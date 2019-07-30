package demo.app.mail.interfaces.dto;

import demo.app.mail.domain.Email;
import demo.app.mail.domain.MailStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itaesu on 29/07/2019.
 */
@ToString
@Getter @AllArgsConstructor
public final class SentMail {
    private String id;
    private String title;
    private String sender;
    private List<String> recipients;
    private String content;
    private Map<String, Object> model;
    private MailStatus mailStatus;
    private Map<String, String> additionalInformation = new HashMap<>();

    private SentMail() {
    }


    public static SentMail from(Email email) {
        return new SentMail(email.getId(), email.getTitle(), email.getSender(), email.getRecipients(), email.getContent(),
                            email.getModel(), email.getStatus(), email.getAdditionalInformation());
    }
}
