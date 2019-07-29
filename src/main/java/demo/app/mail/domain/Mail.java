package demo.app.mail.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * Created by itaesu on 29/07/2019.
 */
@Document @Builder @Getter
public class Mail {

    @Id
    private String id;
    private String subject;
    private String content;
    private String sender;
    private List<String> recipients;
    private Map<String, Object> model;
}
