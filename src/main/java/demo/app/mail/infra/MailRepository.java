package demo.app.mail.infra;

import demo.app.mail.domain.Mail;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


/**
 * Created by itaesu on 29/07/2019.
 */
public interface MailRepository extends ReactiveMongoRepository<Mail, String> {
    Flux<Mail> findAllBySender(String sender);
}
