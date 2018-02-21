/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.base.exception.param;

import com.hnpc.exchange.base.exception.BusinessException;

/**
 * exchanger
 * Created by Damon on 08/02/2018.
 */
public class BlankParameterException extends BusinessException {
  public BlankParameterException() {
  }

  public BlankParameterException(String message) {
    super(message);
  }

  public BlankParameterException(String format, Object... objects) {
    super(format, objects);
  }
}
