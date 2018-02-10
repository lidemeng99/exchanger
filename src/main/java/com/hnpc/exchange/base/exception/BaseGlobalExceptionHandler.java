/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.base.exception;

import com.hnpc.exchange.base.exception.param.BlankParameterException;
import com.hnpc.exchange.base.message.model.DefaultErrorResult;
import com.hnpc.exchange.base.message.model.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;


/**
 * exchanger
 * Created by Damon on 08/02/2018.
 */
@Slf4j
public class BaseGlobalExceptionHandler {

    /**
     * 处理验证参数封装错误时异常
     */
    protected DefaultErrorResult handleBlankParameterException(BlankParameterException e, HttpServletRequest request) {
        log.info("handleBlankParameterException start, uri:{}, caused by: ", request.getRequestURI(), e);
        return DefaultErrorResult.failure(ResultCode.PARAM_IS_BLANK, e, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理验证参数类型错误时异常
     */
    protected DefaultErrorResult handleTypeBindErrorParameterException(BlankParameterException e, HttpServletRequest request) {
        log.info("handleTypeBindErrorParameterException start, uri:{}, caused by: ", request.getRequestURI(), e);
        return DefaultErrorResult.failure(ResultCode.PARAM_TYPE_BIND_ERROR, e, HttpStatus.BAD_REQUEST);
    }

//    /**
//     * 违反约束异常
//     */
//    protected DefaultErrorResult handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
//        log.info("handleConstraintViolationException start, uri:{}, caused by: ",
//                request.getRequestURI(), e);
//        List<ParameterInvalidItem> parameterInvalidItemList = ConvertUtil.convertCVSetToParameterInvalidItemList(e.getConstraintViolations());
//        return DefaultErrorResult.failure(ResultCode.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST, parameterInvalidItemList);
//    }
//
//    /**
//     * 处理验证参数封装错误时异常
//     */
//    protected DefaultErrorResult handleConstraintViolationException(HttpMessageNotReadableException e, HttpServletRequest request) {
//        log.info("handleConstraintViolationException start, uri:{}, caused by: ", request.getRequestURI(), e);
//        return DefaultErrorResult.failure(ResultCode.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST);
//    }
//
//    /**
//     * 处理参数绑定时异常（反400错误码）
//     */
//    protected DefaultErrorResult handleBindException(BindException e, HttpServletRequest request) {
//        log.info("handleBindException start, uri:{}, caused by: ", request.getRequestURI(), e);
//        List<ParameterInvalidItem> parameterInvalidItemList = ConvertUtil.convertBindingResultToMapParameterInvalidItemList(e.getBindingResult());
//        return DefaultErrorResult.failure(ResultCode.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST, parameterInvalidItemList);
//    }
//
//    /**
//     * 处理使用@Validated注解时，参数验证错误异常（反400错误码）
//     */
//    protected DefaultErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
//        log.info("handleMethodArgumentNotValidException start, uri:{}, caused by: ", request.getRequestURI(), e);
//        List<ParameterInvalidItem> parameterInvalidItemList = ConvertUtil.convertBindingResultToMapParameterInvalidItemList(e.getBindingResult());
//        return DefaultErrorResult.failure(ResultCode.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST, parameterInvalidItemList);
//    }

    /**
     * 处理通用自定义业务异常
     */
    protected ResponseEntity<DefaultErrorResult> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.info("handleBusinessException start, uri:{}, exception:{}, caused by: {}", request.getRequestURI(), e.getClass(), e.getMessage());

        DefaultErrorResult defaultErrorResult = DefaultErrorResult.failure(e);
        return ResponseEntity
                .status(HttpStatus.valueOf(defaultErrorResult.getStatus()))
                .body(defaultErrorResult);
    }

    /**
     * 处理运行时系统异常（反500错误码）
     */
    protected DefaultErrorResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        log.error("handleRuntimeException start, uri:{}, caused by: ", request.getRequestURI(), e);
        return DefaultErrorResult.failure(ResultCode.SYSTEM_INNER_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
