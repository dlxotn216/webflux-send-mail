package demo.app.utils;

import demo.app.message.application.service.MessageService;

import static demo.app.utils.StaticContextAccessor.getBean;

/**
 * Created by itaesu on 30/07/2019.
 */
public final class MessageUtils {
    private MessageUtils() {

    }

    public static String getLocalizedMessage(String messageId, Object... args) {
        return getBean(MessageService.class).getLocalizedMessage(messageId, args);
    }
}

