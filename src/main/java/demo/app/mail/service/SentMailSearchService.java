package demo.app.mail.service;

import demo.app.mail.infra.MailRepository;
import demo.app.mail.interfaces.dto.SentMail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


/**
 * Created by itaesu on 29/07/2019.
 */
@Component @RequiredArgsConstructor
public class SentMailSearchService {
    private final MailRepository mailRepository;

    public Flux<SentMail> searchAllBySender(String sender){
        return this.mailRepository.findAllBySender(sender)
                .map(SentMail::from);
    }
}
