/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.redis.configuration;

import java.util.logging.Logger;

import redis.clients.jedis.JedisPoolConfig;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * exchanger
 * Created by Damon on 29/01/2018.
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

  private static Logger logger = Logger.getLogger(RedisConfig.class.getName());

  @Bean
  @ConfigurationProperties(prefix = "spring.redis")
  public JedisPoolConfig getRedisConfig() {
    JedisPoolConfig config = new JedisPoolConfig();
    return config;
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.redis")
  public JedisConnectionFactory getConnectionFactory() {
    JedisConnectionFactory factory = new JedisConnectionFactory();
    JedisPoolConfig config = getRedisConfig();
    factory.setPoolConfig(config);
    logger.info("JedisConnectionFactory bean init success.");
    return factory;
  }


  @Bean
  public RedisTemplate<?, ?> getStringRedisTemplate() {
    RedisTemplate<?, ?> template = new StringRedisTemplate(getConnectionFactory());
    return template;
  }
}