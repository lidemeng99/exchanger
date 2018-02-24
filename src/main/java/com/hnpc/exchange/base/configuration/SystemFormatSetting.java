/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

package com.hnpc.exchange.base.configuration;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * exchanger
 * Created by Damon on 24/02/2018.
 */
@ConfigurationProperties(prefix = "system.format")
@Data
public class SystemFormatSetting {
  private String date_format;
}
