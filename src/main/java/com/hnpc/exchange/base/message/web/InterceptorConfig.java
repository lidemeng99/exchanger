/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.base.message.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * exchanger
 * Created by Damon on 08/02/2018.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
  @Autowired
  private ResponseResultInterceptor responseResultInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    String apiUri = "/**";
    //响应结果控制拦截
    registry.addInterceptor(responseResultInterceptor).addPathPatterns(apiUri);
  }
}
