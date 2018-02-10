package com.hnpc.exchange.base.message.model;

import java.lang.annotation.*;

/**
 * exchanger
 * Created by Damon on 08/02/2018.
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseResult {
    Class<? extends Result>  value() default PlatformResult.class;
}
