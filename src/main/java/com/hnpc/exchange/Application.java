/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

/**
 * exchanger
 * Created by Damon on 29/01/2018.
 */
package com.hnpc.exchange;

import com.hnpc.exchange.redis.configuration.RedisSetting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
@EnableConfigurationProperties({RedisSetting.class})
//@SpringBootApplication(exclude = {
//        DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class})
public class Application {
    //extends SpringBootServletInitializer
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

