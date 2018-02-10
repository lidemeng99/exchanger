/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.redis.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnpc.exchange.base.exception.param.TypeBindErrorParameterException;
import com.hnpc.exchange.base.message.model.ResponseResult;
import com.hnpc.exchange.news.manager.dto.News;
import com.hnpc.exchange.redis.configuration.RedisSetting;
import com.hnpc.exchange.redis.manager.RedisClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * exchanger
 * Created by Damon on 29/01/2018.
 */
@ResponseResult
@RestController
@RequestMapping("/api/v1.0/cache")
public class RedisClientTest {
    @Autowired
    private RedisClientManager redisClientManager;

    @Autowired
    private RedisSetting redisSetting;

    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "")
    public @ResponseBody
    News addKey(@RequestBody News news) {
        String key=news.getColumnid()+redisSetting.getKeysplitter()+news.getNewsid();
        news.setLastModifytime(new Date().getTime());
        String value= null;
        try {
            value = mapper.writeValueAsString(news);
            redisClientManager.insert(key,value);
            return news;
        } catch (JsonProcessingException e) {
            throw new TypeBindErrorParameterException(e.getCause());
        }

    }

}
