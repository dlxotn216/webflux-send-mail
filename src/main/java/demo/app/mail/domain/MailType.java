package demo.app.mail.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by itaesu on 30/07/2019.
 */
@AllArgsConstructor @Getter
public enum MailType {

    CTMS_USER_REGISTRATION("/ctms/registered_user/%s.ftl", "CTMS_USER_REGISTRATION", LogoType.CTMS)
    ;

    private String templateLocation;
    private String i18nCode;
    private LogoType logoType;
}