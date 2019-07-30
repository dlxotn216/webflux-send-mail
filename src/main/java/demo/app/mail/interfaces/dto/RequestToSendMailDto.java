package demo.app.mail.interfaces.dto;

import demo.app.mail.domain.Email;
import demo.app.mail.domain.MailStatus;
import demo.app.mail.domain.MailType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

import static demo.app.constants.ApplicationConstant.SENDER;
import static java.lang.String.format;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

/**
 * Created by itaesu on 29/07/2019.
 */
public final class RequestToSendMailDto {

    private RequestToSendMailDto() {
    }

    @Data @AllArgsConstructor @ToString @NoArgsConstructor
    public static class Request {
        private String sender;
        private MailType mailType;
        private List<String> titleParameters;
        private List<String> recipients;
        private Map<String, Object> model;


        public String getTemplatePath() {
            return format(mailType.getLocation(), getLocale()).toLowerCase();
        }

        public String getSender() {
            return StringUtils.isEmpty(this.sender)
                    ? SENDER
                    : this.sender;
        }

        public String getTitleI18nCode() {
            return this.mailType.getI18nCode();
        }
    }

    @Data @AllArgsConstructor @ToString @NoArgsConstructor
    public static class Response {
        private String messageId;
        private String sender;
        private List<String> recipients;
        private String title;
        private String content;
        private MailStatus status;
        private Map<String, String> additionalInformation;

        public static Response from(Email email) {
            return new Response(email.getId(), email.getSender(), email.getRecipients(), email.getTitle(),
                                email.getContent(), email.getStatus(), email.getAdditionalInformation());
        }

    }
}
