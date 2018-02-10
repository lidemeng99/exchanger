/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.redis.services;

import lombok.Builder;
import lombok.Data;

/**
 * exchanger
 * Created by Damon on 08/02/2018.
 */
@Builder
@Data
public class User {
    private String username;
    private String password;
}
