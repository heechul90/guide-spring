package com.spring.guide.global.json;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JsonResponse<T> {

    private LocalDateTime transaction_time;
    private String message;
    private int status;
    private String code;

    @Nullable
    private T data;

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    private JsonResponse(final JsonCode code, final @Nullable T data, MessageSource messageSource) {
        this.transaction_time = LocalDateTime.now();
        this.message = messageSource.getMessage(code.getMessage(), new Object[]{}, DEFAULT_SUCCESS_MESSAGE, LocaleContextHolder.getLocale());
        this.status = code.getStatus();
        this.code = code.getCode();
        this.data = data;
    }

    private JsonResponse(final JsonCode code, MessageSource messageSource) {
        this.transaction_time = LocalDateTime.now();
        this.message = messageSource.getMessage(code.getMessage(), new Object[]{}, DEFAULT_SUCCESS_MESSAGE, LocaleContextHolder.getLocale());
        this.status = code.getStatus();
        this.code = code.getCode();
        this.data = null;
    }

    private JsonResponse(final JsonCode code, String customMessage) {
        this.transaction_time = LocalDateTime.now();
        this.message = customMessage;
        this.status = code.getStatus();
        this.code = code.getCode();
        this.data = null;
    }

    public static <T> JsonResponse<T> of(final JsonCode code, final @Nullable T data, MessageSource messageSource) {
        return new JsonResponse(code, data, messageSource);
    }

    public static JsonResponse of(final JsonCode code, MessageSource messageSource) {
        return new JsonResponse(code, messageSource);
    }

    public static JsonResponse of(final JsonCode code, final String customMessage) {
        return new JsonResponse(code, customMessage);
    }
}
