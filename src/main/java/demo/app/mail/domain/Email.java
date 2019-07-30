package demo.app.mail.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itaesu on 29/07/2019.
 */
@Document @Builder @Getter
public class Email {
    @Id
    private String id;
    private String title;
    private String content;
    private String sender;
    private MailType mailType;
    private List<String> recipients;
    private Map<String, Object> model;

    @Builder.Default
    private MailStatus status = MailStatus.WAITING;

    @Builder.Default
    private Map<String, String> additionalInformation = new HashMap<>();

    public void processSuccess(Map<String, String> additionalInformation) {
        if (!CollectionUtils.isEmpty(additionalInformation)) {
            additionalInformation.forEach(this::processSuccess);
        }

        this.processSuccess();
    }

    public void processSuccess(String additionalInfoKet, String additionalInfoValue) {
        if (!StringUtils.isEmpty(additionalInfoKet) && !StringUtils.isEmpty(additionalInfoValue)) {
            this.addAdditionalInformation(additionalInfoKet, additionalInfoValue);
        }

        this.processSuccess();
    }

    public void processSuccess() {
        this.status = MailStatus.SUCCESS;
    }

    public void processFail(Throwable throwable) {
        if (throwable != null) {
            this.addAdditionalInformation("errorMessage", throwable.getMessage());
        }

        this.processFail();
    }

    public void processFail() {
        this.status = MailStatus.FAIL;

    }

    public void addAdditionalInformation(String key, String value) {
        this.additionalInformation.put(key, value);
    }
}
