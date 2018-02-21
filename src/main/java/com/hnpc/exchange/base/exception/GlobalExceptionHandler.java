/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.base.exception;

import com.hnpc.exchange.base.exception.param.BlankParameterException;
import com.hnpc.exchange.base.message.model.DefaultErrorResult;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * exchanger
 * Created by Damon on 08/02/2018.
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends BaseGlobalExceptionHandler {
  //    /* 处理400类异常 */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(ConstraintViolationException.class)
//    public DefaultErrorResult handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
//        return super.handleConstraintViolationException(e, request);
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public DefaultErrorResult handleConstraintViolationException(HttpMessageNotReadableException e, HttpServletRequest request) {
//        return super.handleConstraintViolationException(e, request);
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(BindException.class)
//    public DefaultErrorResult handleBindException(BindException e, HttpServletRequest request) {
//        return super.handleBindException(e, request);
//    }
//
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BlankParameterException.class)
  public DefaultErrorResult handleBlankParameterException(BlankParameterException e, HttpServletRequest request) {
    return super.handleBlankParameterException(e, request);
  }

  /* 处理自定义异常 */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<DefaultErrorResult> handleBusinessException(BusinessException e, HttpServletRequest request) {
    return super.handleBusinessException(e, request);
  }

  /* 处理运行时异常 */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  public DefaultErrorResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
    //TODO 可通过邮件、微信公众号等方式发送信息至开发人员、记录存档等操作（这个后面我们文章我们单独说明该怎么处理）
    return super.handleRuntimeException(e, request);
  }
}
