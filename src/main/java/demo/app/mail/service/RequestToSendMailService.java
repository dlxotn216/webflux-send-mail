package demo.app.mail.service;

import demo.app.mail.domain.Email;
import demo.app.mail.infra.EmailRepository;
import demo.app.mail.infra.EmailSender;
import demo.app.mail.interfaces.dto.RequestToSendMailDto.Request;
import demo.app.mail.interfaces.dto.RequestToSendMailDto.Response;
import freemarker.core.InvalidReferenceException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfig;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static demo.app.utils.MessageUtils.getLocalizedMessage;
import static java.lang.String.format;

/**
 * Created by itaesu on 29/07/2019.
 */
@Slf4j
@Component @RequiredArgsConstructor
public class RequestToSendMailService {
    private final FreeMarkerConfig freemarkerConfig;
    private final EmailRepository emailRepository;
    private final EmailSender awsMailSendService;

    public Mono<Response> requestToSend(Request request) {
        return this.emailRepository.save(build(request))
                                   .doOnSuccess(this::sendMail)
                                   .map(Response::from);
    }

    private void sendMail(Email email) {
        this.awsMailSendService.send(email)
                               .thenAccept(sendEmailResult -> {
                                   email.processSuccess(sendEmailResult);
                                   this.emailRepository.save(email).subscribe();
                               })
                               .exceptionally(throwable -> {
                                   email.processFail(throwable);
                                   this.emailRepository.save(email).log().subscribe();
                                   return null;
                               });
    }

    private Email build(Request request) {
        return Email.builder().sender(request.getSender())
                    .mailType(request.getMailType())
                    .title(getLocalizedMessage(request.getTitleI18nCode(), request.getTitleParameters().toArray()))
                    .content(getContent(request))
                    .model(request.getModel())
                    .recipients(request.getRecipients())
                    .build();
    }

    private String getContent(Request request) {
        try {
            final Template template = freemarkerConfig.getConfiguration().getTemplate(request.getTemplatePath());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, request.getModel());
        } catch (IOException | TemplateException e) {
            if (e instanceof InvalidReferenceException) {
                final String errorMessage
                        = format("Require parameter [%s] is not present",
                                 ((InvalidReferenceException) e).getBlamedExpressionString());

                log.error("Cannot load template cause: {}", errorMessage);
                throw new IllegalArgumentException(errorMessage);
            } else if (e instanceof TemplateNotFoundException) {
                final String errorMessage
                        = format("Could not load template %s", ((TemplateNotFoundException) e).getTemplateName());

                log.error("Cannot load template cause: {}", errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            log.error("Cannot load template cause: {}", e);
            throw new IllegalArgumentException(e);

        }
    }
}
