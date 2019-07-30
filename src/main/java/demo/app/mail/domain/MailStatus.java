package demo.app.mail.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by itaesu on 30/07/2019.
 */
@AllArgsConstructor @Getter
public enum MailStatus {

    WAITING("Waiting"),
    FAIL("Fail"),
    SUCCESS("Success")
    ;

    private String code;
}
