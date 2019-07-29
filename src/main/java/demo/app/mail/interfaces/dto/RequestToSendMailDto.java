package demo.app.mail.interfaces.dto;

import demo.app.mail.domain.Mail;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by itaesu on 29/07/2019.
 *
 */
public final class RequestToSendMailDto {

    private RequestToSendMailDto() {
    }

    @Data @AllArgsConstructor
    public static class Request {
        private String subject;
        private List<String> recipients;
    }

    @Data @AllArgsConstructor
    public static class Response {
        private String messageId;
        private String status;

        public static Response from(Mail mail) {
            return new Response(mail.getId(), "send ");
        }
    }
}
