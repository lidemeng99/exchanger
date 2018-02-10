/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.base.exception.param;

import com.hnpc.exchange.base.exception.BusinessException;

/**
 * exchanger
 * Created by Damon on 09/02/2018.
 */
public class TypeBindErrorParameterException extends BusinessException {
    public TypeBindErrorParameterException() {
    }

    public TypeBindErrorParameterException(String message) {
        super(message);
    }

    public TypeBindErrorParameterException(Throwable cause, Object... objects) {
        super(cause.getMessage(), cause, new Object[]{});
    }

    public TypeBindErrorParameterException(String msg, Throwable cause, Object... objects) {
        super(msg, cause, objects);
    }
}
