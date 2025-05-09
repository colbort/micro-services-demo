package com.third.games.user.handler;

import com.third.games.common.exception.BizException;
import com.third.games.common.result.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 业务异常（你自定义的）
    @ExceptionHandler(BizException.class)
    public Result<?> handleBizException(BizException ex) {
        return Result.fail(ex.getCode(), ex.getMessage());
    }

    // 参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Result.fail(400, msg);
    }

    // 请求参数缺失
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleMissingParam(MissingServletRequestParameterException ex) {
        return Result.fail(400, "缺少请求参数: " + ex.getParameterName());
    }

    // 兜底异常
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception ex) {
        ex.printStackTrace(); // 打印日志（也可用 log.error）
        return Result.fail(500, "服务器内部异常");
    }
}

