/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.redis.manager;

import com.hnpc.exchange.base.exception.param.BlankParameterException;
import com.hnpc.exchange.redis.configuration.RedisSetting;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * exchanger
 * Created by Damon on 08/02/2018.
 */
@Component
public class RedisClientManager {
  private static Log logger = LogFactory.getLog(RedisClientManager.class);

  @Autowired
  private RedisTemplate<String, String> jsonObjectRedisTemplate;
  @Autowired
  private RedisSetting redisSetting;

  /**
   * insert JSONOBJECT to redis
   *
   * @param key        key
   * @param jsonObject value
   */
  public void insert(String key, String jsonObject) {
    if (StringUtils.isBlank(key) || jsonObject == null) {
      throw new BlankParameterException();
    }
    jsonObjectRedisTemplate.opsForValue().set(key, jsonObject);
  }


  /**
   * insert JSONOBJECT to redis's table
   *
   * @param tablename  table
   * @param key        key
   * @param jsonObject value
   */
  public void insert(String tablename, String key, String jsonObject) {
    if (StringUtils.isBlank(tablename)) {
      insert(key, jsonObject);
    } else {
      String rediskey = StringUtils.join(new String[]{tablename, key}, redisSetting.getKeysplitter());
      jsonObjectRedisTemplate.opsForValue().set(rediskey, jsonObject);
    }

  }
}
