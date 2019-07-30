package demo.app.mail.service;

import demo.app.mail.domain.MailType;
import demo.app.mail.interfaces.dto.RequestToSendMailDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static demo.app.constants.ApplicationConstant.SENDER;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

/**
 * Created by itaesu on 30/07/2019.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestToSendEmailServiceTest {

    @Autowired
    private SentMailSearchService sentMailSearchService;

    @Autowired
    private RequestToSendMailService sendMailService;

    @Test
    public void 정상적인_파라미터를_이용한_메일전송_요청_테스트() {
        //Given
        final List<String> recipients = Arrays.asList("taesu@crscube.co.kr", "dlxotn216@gmail.com");
        final RequestToSendMailDto.Request request
                = new RequestToSendMailDto.Request(null, MailType.CTMS_USER_REGISTRATION,
                                                   singletonList("BETA"),
                                                   recipients,
                                                   Stream.of(new String[][]{
                                                           {"passwordURL", "www.cubectms.com"},
                                                           {"sponsorName", "CRSCUBE"},
                                                           {"username", "Lee Tae Su"},
                                                           {"userId", "taesu"},
                                                           {"systemURL", "www.cubectms.com"}
                                                   }).collect(Collectors.toMap(o -> o[0], o -> o[1])));


        //When
        final Mono<RequestToSendMailDto.Response> responseMono = this.sendMailService.requestToSend(request);

        //then
        StepVerifier.create(responseMono)
                    .expectNextMatches(response -> {
                        then(response.getMessageId()).isNotNull();
                        then(response.getTitle()).isEqualTo("[cubeCTMS] BETA User Registration");
                        then(response.getContent()).isNotNull();
                        then(response.getRecipients()).isEqualTo(recipients);
                        then(response.getSender()).isEqualTo(SENDER);
                        return true;
                    })
                    .expectComplete()
                    .verify();


        StepVerifier.create(this.sentMailSearchService.searchAllBySender(SENDER))
                    .expectNextMatches(sentMail -> {
                        then(sentMail.getId()).isNotNull();
                        then(sentMail.getTitle()).isEqualTo("[cubeCTMS] BETA User Registration");
                        then(sentMail.getContent()).isNotNull();
                        then(sentMail.getRecipients()).isEqualTo(recipients);
                        then(sentMail.getSender()).isEqualTo(SENDER);

                        log.info("result {}", sentMail);
                        return true;
                    })
                    .expectComplete()
                    .verify();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 필수_바인딩_파라미터를_누락한_메일전송_요청_테스트() {
        //Given
        final List<String> recipients = Arrays.asList("taesu@crscube.co.kr", "dlxotn216@gmail.com");
        final RequestToSendMailDto.Request request
                = new RequestToSendMailDto.Request(null, MailType.CTMS_USER_REGISTRATION,
                                                   singletonList("BETA"),
                                                   recipients,
                                                   Stream.of(new String[][]{
                                                           {"passwordURL", "www.cubectms.com"},
                                                           {"sponsorName", "CRSCUBE"},
                                                           {"systemURL", "www.cubectms.com"}
                                                   }).collect(Collectors.toMap(o -> o[0], o -> o[1])));


        //When
        final Mono<RequestToSendMailDto.Response> responseMono = this.sendMailService.requestToSend(request);

        //then
        //suite will not be attempt
        assertThat("empty").isNull();
    }


}