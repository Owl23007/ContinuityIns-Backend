package org.ContinuityIns.exception;


import org.ContinuityIns.entity.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 全局异常处理
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理自定义异常
     * @Author:      MoXueYao
     * */

    // 处理所有异常
    @ExceptionHandler(Exception.class)
    public Result handException(Exception e) {
        // 打印异常信息
        e.printStackTrace();
        // 返回错误信息
        return Result.error(StringUtils.hasLength(e.getMessage())?e.getMessage():"操作失败。");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Result> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = "用户名为5-16位英文数字组合";
        return ResponseEntity.badRequest().body(Result.error(errorMessage));
    }
}
