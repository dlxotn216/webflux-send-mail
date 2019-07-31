package demo.app.mail.infra.impl;

import com.amazonaws.AmazonClientException;
import demo.app.config.AppConstantsConfigurationProperties;
import demo.app.mail.domain.Email;
import demo.app.mail.infra.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.ses.SesAsyncClient;
import software.amazon.awssdk.services.ses.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by itaesu on 30/07/2019.
 */
@Slf4j
@Component("AwsAsyncEmailSender") @RequiredArgsConstructor
public class AwsAsyncEmailSender implements EmailSender {

    private final AppConstantsConfigurationProperties properties;
    private final SesAsyncClient sesAsyncClient;

    @Async
    @Override
    public CompletableFuture<Map<String, String>> send(Email email) {
        try {
            final SendEmailRequest build = getSendEmailRequest(email);
            return this.sesAsyncClient.sendEmail(build)
                                      .thenApply(sendEmailResult -> {
                                          final Map<String, String> successInfo = new HashMap<>();
                                          successInfo.put(this.properties.getAwsMessageIdKeyName(),
                                                          sendEmailResult.messageId());
                                          return successInfo;
                                      });
        } catch (Exception ex) {
            throw new AmazonClientException(ex.getMessage(), ex);
        }
    }

    private SendEmailRequest getSendEmailRequest(Email email) {
        return SendEmailRequest.builder()
                               .destination(
                                       Destination.builder().toAddresses(email.getRecipients()).build())
                               .message(Message.builder()
                                              .subject(createContent(email.getTitle()))
                                              .body(Body.builder().html(createContent(email.getContent())).build())
                                              .build())
                               .source(email.getSender()).build();
    }

    private Content createContent(String text) {
        return Content.builder()
                      .charset(this.properties.getAwsMailContentCharset())
                      .data(text)
                      .build();
    }
}
