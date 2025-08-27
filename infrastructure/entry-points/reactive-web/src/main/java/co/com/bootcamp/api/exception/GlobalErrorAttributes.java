package co.com.bootcamp.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = getError(request);
        log.error("‼️ An error has occurred: ", error);
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("message", getProblem(error.getMessage()));
        errorAttributes.put("path", request.path());
        errorAttributes.put("timestamp", new Date());

        return errorAttributes;
    }

    private String getProblem(String message) {
        String newMessage = message == null ? "" : message;
        String[] parts = newMessage.split("problem:");
        if (parts.length > 1) {
            return parts[1].trim();
        }

        return newMessage.trim();
    }
}
