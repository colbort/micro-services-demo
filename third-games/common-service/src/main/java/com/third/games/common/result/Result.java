package com.third.games.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(0, "OK", data);
    }

    public static Result<?> success() {
        return success(null);
    }

    public static Result<?> fail(String message) {
        return new Result<>(500, message, null);
    }

    public static Result<?> fail(int code, String message) {
        return new Result<>(code, message, null);
    }
}

