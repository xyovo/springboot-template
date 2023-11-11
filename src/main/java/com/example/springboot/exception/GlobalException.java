package com.example.springboot.exception;

import com.example.springboot.common.Result;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result serviceException(ServiceException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 请求方法错误
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result globalException(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        return Result.error("405", "HTTP请求方法错误");
    }

    /**
     * 文件大小超过最大限制
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public Result globalException(MaxUploadSizeExceededException e) {
        e.printStackTrace();
        return Result.error("400", "文件大小不可超过20MB");
    }

    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result globalException(Exception e) {
        e.printStackTrace();
        return Result.error("500", "服务器内部错误");
    }

}