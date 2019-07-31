package demo.app.mail.interfaces.controller;

import demo.app.abstracts.AbstractController;
import demo.app.mail.interfaces.dto.RequestToSendMailDto.Response;
import demo.app.mail.interfaces.dto.RequestToSendMailDto.SimpleRequest;
import demo.app.mail.service.SimpleRequestToSendMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by itaesu on 29/07/2019.
 */
@Slf4j
@RestController @RequiredArgsConstructor
public class RequestToSendMailController extends AbstractController {
    private final SimpleRequestToSendMailService simpleRequestToSendMailService;

    @PostMapping(value = "/mails", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response>> handleRequestToSend(@RequestBody SimpleRequest request) {
        return this.simpleRequestToSendMailService.requestToSend(request)
                                                  .map(response -> ResponseEntity.ok().body(response));
    }
}
