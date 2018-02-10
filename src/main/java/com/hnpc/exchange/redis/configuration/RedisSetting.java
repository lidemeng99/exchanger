/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.redis.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * exchanger
 * Created by Damon on 08/02/2018.
 */
@ConfigurationProperties(prefix = "redis")
public class RedisSetting {
    private String keysplitter; //redis key join string

    public String getKeysplitter() {
        return keysplitter;
    }

    public void setKeysplitter(String keysplitter) {
        this.keysplitter = keysplitter;
    }
}
