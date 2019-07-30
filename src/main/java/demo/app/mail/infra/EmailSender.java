package demo.app.mail.infra;

import demo.app.mail.domain.Email;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by itaesu on 30/07/2019.
 */
public interface EmailSender {

    CompletableFuture<Map<String, String>> send(Email email);
}
