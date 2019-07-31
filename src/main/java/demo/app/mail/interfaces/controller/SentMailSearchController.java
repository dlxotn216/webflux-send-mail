package demo.app.mail.interfaces.controller;

import demo.app.abstracts.AbstractController;
import demo.app.mail.interfaces.dto.SentMailSearchDto;
import demo.app.mail.service.SentMailSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Created by itaesu on 29/07/2019.
 */
@RestController @RequiredArgsConstructor
public class SentMailSearchController extends AbstractController {
    private final SentMailSearchService sentMailSearchService;

    @GetMapping("/mails")
    public ResponseEntity<Flux<SentMailSearchDto>> searchAllBySender(@RequestParam("sender") String sender) {
        return ResponseEntity.ok(this.sentMailSearchService.searchAllBySender(sender));
    }
}
