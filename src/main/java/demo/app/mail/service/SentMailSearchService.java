package demo.app.mail.service;

import demo.app.mail.infra.EmailRepository;
import demo.app.mail.interfaces.dto.SentMail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


/**
 * Created by itaesu on 29/07/2019.
 */
@Slf4j
@Component @RequiredArgsConstructor
public class SentMailSearchService {
    private final EmailRepository emailRepository;

    public Flux<SentMail> searchAllBySender(String sender){
        return this.emailRepository.findAllBySender(sender).map(SentMail::from);
    }
}
