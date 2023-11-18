package io.realworld.backend.app.exception;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.MustacheException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;

public class ExceptionMessageAccessor {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionMessageAccessor.class);

    protected final MessageSourceAccessor messageSourceAccessor;

    public ExceptionMessageAccessor(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    public String getMessage(String code, String defaultMessage, Map<String, Object> context) {
        String msg = messageSourceAccessor.getMessage(code, defaultMessage);
        if (msg == null || msg.isEmpty()) {
            msg = code;
        }
        if (StringUtils.isNotEmpty(msg)) {
            try {
                Map<String, Object> scope = new HashMap<>();
                if (context != null) {
                    scope.putAll(context);
                }

                msg = Mustache.compiler().defaultValue("").compile(msg).execute(scope);
            } catch (MustacheException me) {
                logger.error("Error while templating mustache {} {} ", code, msg, me);
            }
        } else {
            msg = "";
        }

        return msg;
    }
}
