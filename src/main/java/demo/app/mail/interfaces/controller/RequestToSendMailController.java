package demo.app.mail.interfaces.controller;

import demo.app.abstracts.AbstractController;
import demo.app.mail.interfaces.dto.RequestToSendMailDto.Request;
import demo.app.mail.interfaces.dto.RequestToSendMailDto.Response;
import demo.app.mail.service.RequestToSendMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by itaesu on 29/07/2019.
 */
@RestController @RequiredArgsConstructor
public class RequestToSendMailController extends AbstractController {
    private final RequestToSendMailService requestToSendMailService;

    @PostMapping("/mails")
    public Mono<ResponseEntity<Response>> handleRequestToSend(@RequestBody Request request) {
        return this.requestToSendMailService.requestToSend(request)
                                            .map(response -> ResponseEntity.ok().body(response));
    }
}
