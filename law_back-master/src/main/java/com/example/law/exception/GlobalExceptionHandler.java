package com.example.law.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义参数错误异常处理器
     *
     * @param e 自定义参数
     * @return ResultResponse
     */
    @ResponseBody
    @ExceptionHandler({BizException.class})
    public ResultResponse bizExceptionHandler(BizException e) {
        log.error(e.toString());
        return ResultResponse.error(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * satoken未登录异常
     *
     * @param e 自定义参数
     * @return ResultResponse
     */
    @ResponseBody
    @ExceptionHandler({NotLoginException.class})
    public ResultResponse satokenExceptionHandler(NotLoginException e) {
        log.error("satoken未登录异常！原因是:", e);
        return ResultResponse.error(String.valueOf(e.getCode()), e.getMessage());
    }

    /**
     * satoken角色异常
     *
     * @param e 自定义参数
     * @return ResultResponse
     */
    @ResponseBody
    @ExceptionHandler({NotRoleException.class})
    public ResultResponse satokenExceptionHandler(NotRoleException e) {
        log.error("satoken角色异常！原因是:", e);
        return ResultResponse.error(String.valueOf(e.getCode()), e.getMessage());
    }


    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
     *
     * @Validated @Valid仅对于表单提交有效，对于以json格式提交将会失效）
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public ResultResponse BindExceptionHandler(BindException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        List<String> msgList = new ArrayList<>();
        for (ObjectError allError : allErrors) {
            msgList.add(allError.getDefaultMessage());
        }
        log.error(msgList.toString());
        return ResultResponse.error(msgList.toString());
    }

    /**
     * @param e 异常类
     * @return 响应
     * @Validated @Valid 前端提交的方式为json格式有效
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultResponse MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        List<String> msgList = new ArrayList<>();
        for (ObjectError allError : allErrors) {
            msgList.add(allError.getDefaultMessage());
        }
        log.error(msgList.toString());
        return ResultResponse.error(msgList.toString());
    }


    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     *
     * @param e
     * @return
     * @NotBlank @NotNull @NotEmpty
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultResponse ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        List<String> msgList = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<?> cvl = iterator.next();
            msgList.add(cvl.getMessageTemplate());
        }
        log.error(msgList.toString());
        return ResultResponse.error(msgList.toString());
    }

    /**
     * 处理其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultResponse exceptionHandler(Exception e) {
        log.error("未知异常！原因是:", e);
        return ResultResponse.error(e.getMessage());
    }
}


