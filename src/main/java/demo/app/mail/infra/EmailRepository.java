package demo.app.mail.infra;

import demo.app.mail.domain.Email;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


/**
 * Created by itaesu on 29/07/2019.
 */
public interface EmailRepository extends ReactiveMongoRepository<Email, String> {
    Flux<Email> findAllBySender(String sender);
}
