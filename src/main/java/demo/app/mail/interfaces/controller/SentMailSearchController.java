package demo.app.mail.interfaces.controller;

import demo.app.abstracts.AbstractController;
import demo.app.mail.service.SentMailSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * Created by itaesu on 29/07/2019.
 */
@RestController @RequiredArgsConstructor
public class SentMailSearchController extends AbstractController {
    private final SentMailSearchService sentMailSearchService;

    @GetMapping("/mails")
    public Flux<ServerResponse> handleRequestToSend(@RequestParam("sender") String sender) {
        return this.sentMailSearchService.searchAllBySender(sender)
                                         .flatMap(response -> ServerResponse.ok().body(fromObject(response)));
    }
}
