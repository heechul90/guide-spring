package com.spring.guide.global.json;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private JsonResponse(final JsonCode code) {
        this.transaction_time = LocalDateTime.now();
        this.message = DEFAULT_SUCCESS_MESSAGE;
        this.status = code.getStatus();
        this.code = code.getCode();
    }

    private JsonResponse(final JsonCode code, final @Nullable T data) {
        this.transaction_time = LocalDateTime.now();
        this.message = DEFAULT_SUCCESS_MESSAGE;
        this.status = code.getStatus();
        this.code = code.getCode();
        this.data = data;
    }

    public static JsonResponse of(final JsonCode code) {
        return new JsonResponse(code);
    }

    public static <T> JsonResponse<T> of(final JsonCode code, final @Nullable T data) {
        return new JsonResponse<T>(code, data);
    }
}
