package com.epam.esm.web.exception;

import com.epam.esm.service.exception.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {
    public static final String BASE_NAME = "error.";
    public static final String VERSION = "01";
    private MessageSource messageSource;

    @Autowired
    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<Map<String, String>> handleApiResponseException(ResponseException e, Locale locale) {
        Map<String, String> errorResponse = new HashMap<>();

        String message = messageSource.getMessage(BASE_NAME + e.getHttpStatus().name().toLowerCase(), null, locale);

        if (e.getParams() != null) {
            final String infoMessage = e.getParams().length > 1 ?
                    messageSource.getMessage("info.wrong_params", e.getParams(), locale) :
                    messageSource.getMessage("info.wrong_date", null, locale);
            message += ", " + infoMessage;
        }

        errorResponse.put("errorMessage", message);
        errorResponse.put("errorCode", e.getHttpStatus().value() + VERSION);
        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex, Locale locale) {
        Map<String, String> errorResponse = new HashMap<>();
        String message = messageSource.getMessage("error.unexpected", null, locale);

        errorResponse.put("errorMessage", message);
        errorResponse.put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value() + VERSION);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
