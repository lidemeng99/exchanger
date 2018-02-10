/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.news.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnpc.exchange.base.exception.param.BlankParameterException;
import com.hnpc.exchange.news.manager.dto.News;
import com.hnpc.exchange.redis.configuration.RedisSetting;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * exchanger
 * Created by Damon on 09/02/2018.
 */
@Component
@Slf4j
public class NewsQueryManager {

    @Autowired
    private RedisTemplate<String,String> stringRedisTemplate;
    @Autowired
    private RedisSetting redisSetting;
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * query news from cache by column id
     * @param columnId 栏目号
     * @return List<News> {@link com.hnpc.exchange.news.manager.dto.News}
     */
    public List<News> queryFromCacheByColumn(String columnId){
        if(StringUtils.isBlank(columnId)){
            throw new BlankParameterException("查询栏目号为空");
        }
        Set<String> newsJsonStrSet=stringRedisTemplate.keys(columnId+redisSetting.getKeysplitter()+"*");
        if(newsJsonStrSet.size()<1){
            return ListUtils.EMPTY_LIST;
        }
        List<News> newsList=new ArrayList<News>(newsJsonStrSet.size());
        for(String newsJsonStr : newsJsonStrSet){
            if(StringUtils.isBlank(newsJsonStr)){
                continue;
            }
            try {
                News news=mapper.readValue(stringRedisTemplate.opsForValue().get(newsJsonStr),News.class);
                newsList.add(news);
            } catch (IOException e) {
                log.error("{} is not JSON format String.",newsJsonStr,e);
            }
        }

        return newsList;
    }
}
