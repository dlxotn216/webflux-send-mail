package demo.app.mail.service;

import demo.app.mail.domain.Mail;
import demo.app.mail.infra.MailRepository;
import demo.app.mail.interfaces.dto.RequestToSendMailDto.Request;
import demo.app.mail.interfaces.dto.RequestToSendMailDto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Created by itaesu on 29/07/2019.
 */
@Component @RequiredArgsConstructor
public class RequestToSendMailService {
    private final MailRepository mailRepository;

    public Mono<Response> requestToSend(Request request) {
        return this.mailRepository.save(build(request)).map(Response::from);
    }

    private Mail build(Request request) {
        return Mail.builder().sender("taesu@crscube.co.kr")
                   .content("aeff")
                   .recipients(request.getRecipients())
                   .build();
    }
}
