package demo.app.mail.infra.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import demo.app.config.AppConstantsConfigurationProperties;
import demo.app.mail.domain.Email;
import demo.app.mail.infra.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder.standard;
import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Created by itaesu on 30/07/2019.
 */
@Slf4j
@Component("AwsEmailSender") @RequiredArgsConstructor
public class AwsEmailSender implements EmailSender {

    private final AppConstantsConfigurationProperties properties;

    @Async
    @Override
    public CompletableFuture<Map<String, String>> send(Email email) {
        try {
            ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
            AmazonSimpleEmailService client = standard()
                    .withCredentials(credentialsProvider)
                    .withRegion(this.properties.getAwsRegion())
                    .build();

            return completedFuture(client.sendEmail(toSendRequestDto(email)))
                    .thenApply(sendEmailResult -> {
                        final Map<String, String> successInfo = new HashMap<>();
                        successInfo.put(this.properties.getAwsMessageIdKeyName(), sendEmailResult.getMessageId());
                        return successInfo;
                    });
        } catch (Exception ex) {
            throw new AmazonClientException(ex.getMessage(), ex);
        }
    }

    private SendEmailRequest toSendRequestDto(Email email) {
        return new SendEmailRequest()
                .withSource(email.getSender())
                .withDestination(new Destination().withToAddresses(email.getRecipients()))
                .withMessage(new Message().withSubject(createContent(email.getTitle()))
                                          .withBody(new Body().withHtml(createContent(email.getContent()))));
    }

    private Content createContent(String text) {
        return new Content()
                .withCharset(this.properties.getAwsMailContentCharset())
                .withData(text);
    }
}
