package com.stex.core.api.tools.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ResourceForbiddenException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceForbiddenException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("Method is not allowed." +
                "You cannot change %s that is in %s %s.", resourceName, fieldValue, fieldName));
        Logger LOGGER = LoggerFactory.getLogger(this.getClass());
        LOGGER.debug("Method is not allowed." +
                "You cannot change [{}] that is in [{}] [{}].", resourceName, fieldValue, fieldName);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
