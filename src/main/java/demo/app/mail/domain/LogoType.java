package demo.app.mail.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by itaesu on 30/07/2019.
 */
@AllArgsConstructor @Getter
public enum LogoType {

    CTMS("/logos/ctms/logo.png", "logo.png")
    ;

    private String logoLocation;
    private String logoName;
}