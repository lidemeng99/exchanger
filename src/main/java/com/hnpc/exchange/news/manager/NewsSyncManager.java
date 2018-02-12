/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.news.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnpc.exchange.base.exception.param.BlankParameterException;
import com.hnpc.exchange.base.exception.param.TypeBindErrorParameterException;
import com.hnpc.exchange.news.manager.dto.News;
import com.hnpc.exchange.news.manager.dto.SyncHistory;
import com.hnpc.exchange.redis.configuration.RedisSetting;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * exchanger
 * Created by Damon on 09/02/2018.
 */
@Component
@Slf4j
public class NewsSyncManager {
    @Autowired
    private RedisTemplate<String,String> jsonObjectRedisTemplate;
    @Autowired
    private RedisSetting redisSetting;
    private ObjectMapper mapper = new ObjectMapper();


    /**
     * 保存新闻对象至缓存
     * @param news {@link News}
     * @return 从缓存中获取到的缓存
     */
    public News saveCache(News news){
        if(news == null){
            throw new BlankParameterException("传入参数{}为空",News.class.getName());
        }
        if(StringUtils.isBlank(news.getColumnid()) || StringUtils.isBlank(news.getNewsid())){
            throw new BlankParameterException("参数中栏目号或新闻编号为空");
        }
        String key=news.getColumnid()+redisSetting.getKeysplitter()+news.getNewsid();

        try {
            news.setLastModifytime(new Date().getTime());
            //如果KEY已经存在，直接覆盖
            if(news.getFlag() == NewsConstantEnum.NEWS_FLAG_DELETE.code()){
                jsonObjectRedisTemplate.delete(key);
                log.info("DELETE key:{} success to cache",key);
            }else if(news.getFlag() == NewsConstantEnum.NEWS_FLAG_CREATE.code() ||
                    news.getFlag() == NewsConstantEnum.NEWS_FLAG_UPDATE.code()){
                String newsJsonStr=mapper.writeValueAsString(news);
                jsonObjectRedisTemplate.opsForValue().set(key,newsJsonStr);
                log.info("SAVE key:{} success to cache",key);
            }else{
                throw new TypeBindErrorParameterException("News.Flag can not set correct");
            }

        } catch (JsonProcessingException e) {
            log.info("save key:{} failure to cache",key);
            throw new TypeBindErrorParameterException("传入的对象为不合法JSON格式");
        }
        return news;
    }

    public SyncHistory find(String columnId){
        if(StringUtils.isBlank(columnId)){
            throw new BlankParameterException("参数中栏目号为空");
        }
        String key="SyncHis:"+columnId;
        String value=jsonObjectRedisTemplate.opsForValue().get(key);
        if(StringUtils.isBlank(value)){

            return new SyncHistory(columnId, null, NumberUtils.INTEGER_ZERO);

        }
        try {
            return mapper.readValue(value,SyncHistory.class);
        } catch (IOException e) {
            throw new TypeBindErrorParameterException("传入的对象为不合法JSON格式");
        }
    }

    public  SyncHistory saveSyncHistory(SyncHistory syncHistory){
        if(syncHistory == null){
            throw new BlankParameterException("传入参数{}为空",News.class.getName());
        }
        if(StringUtils.isBlank(syncHistory.getColumnId())){
            throw new BlankParameterException("参数中栏目号为空");
        }
        String key="SyncHis:"+syncHistory.getColumnId();

        try {
            String syncHistoryJsonStr=mapper.writeValueAsString(syncHistory);
            jsonObjectRedisTemplate.opsForValue().set(key,syncHistoryJsonStr);
            log.info("save key:{} success to cache",key);
        } catch (JsonProcessingException e) {
            log.info("save key:{} failure to cache",key);
            throw new TypeBindErrorParameterException("传入的对象为不合法JSON格式");
        }

        return syncHistory;
    }
}
