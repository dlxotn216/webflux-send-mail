package demo.app.mail.interfaces.dto;

import demo.app.mail.domain.Email;
import demo.app.mail.domain.EmailStatus;
import demo.app.mail.domain.MailType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
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
    public static class SimpleRequest {
        private String sender;
        private MailType mailType;
        private List<String> titleParameters;
        private List<String> recipients;
        private Map<String, Object> model;
        private List<AttachmentMeta> attachmentMetas;

        public String getTemplatePath() {
            return format(mailType.getTemplateLocation(), getLocale()).toLowerCase();
        }

        public String getSender() {
            return StringUtils.isEmpty(this.sender)
                    ? SENDER
                    : this.sender;
        }

        public List<String> getTitleParameters() {
            return CollectionUtils.isEmpty(titleParameters)
                    ? Collections.emptyList()
                    : this.titleParameters;
        }

        public List<String> getRecipients() {
            return CollectionUtils.isEmpty(recipients)
                    ? Collections.emptyList()
                    : this.recipients;
        }

        public Map<String, Object> getModel() {
            return CollectionUtils.isEmpty(model)
                    ? Collections.emptyMap()
                    : this.model;
        }

        public String getTitleI18nCode() {
            return this.mailType.getI18nCode();
        }

        public List<AttachmentMeta> getAttachmentMetas() {
            return CollectionUtils.isEmpty(attachmentMetas)
                    ? Collections.emptyList()
                    : this.attachmentMetas;
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class AttachmentMeta {
        private String attachmentName;
        private String downloadURL;
    }

    @Data @AllArgsConstructor @ToString @NoArgsConstructor
    public static class Response {
        private String messageId;
        private String sender;
        private List<String> recipients;
        private String title;
        private String content;
        private EmailStatus status;
        private Map<String, String> additionalInformation;

        public static Response from(Email email) {
            return new Response(email.getId(), email.getSender(), email.getRecipients(), email.getTitle(),
                                email.getContent(), email.getStatus(), email.getAdditionalInformation());
        }

    }
}
