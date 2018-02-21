/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.base.message.web;

import com.hnpc.exchange.base.message.model.ResponseResult;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * exchanger
 * Created by Damon on 08/02/2018.
 */
@Component(value = "responseResultInterceptor")
public class ResponseResultInterceptor implements HandlerInterceptor {
  public static final String RESPONSE_RESULT = "RESPONSE-RESULT";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (handler instanceof HandlerMethod) {
      final HandlerMethod handlerMethod = (HandlerMethod) handler;
      final Class<?> clazz = handlerMethod.getBeanType();
      final Method method = handlerMethod.getMethod();
      if (clazz.isAnnotationPresent(ResponseResult.class)) {
        request.setAttribute(RESPONSE_RESULT, clazz.getAnnotation(ResponseResult.class));
      } else if (method.isAnnotationPresent(ResponseResult.class)) {
        request.setAttribute(RESPONSE_RESULT, method.getAnnotation(ResponseResult.class));
      }
    }

    return true;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

  }
}
