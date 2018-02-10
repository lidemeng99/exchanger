/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.news.services;

import com.hnpc.exchange.news.manager.NewsConstantEnum;
import com.hnpc.exchange.news.manager.NewsSyncManager;
import com.hnpc.exchange.news.manager.dto.News;
import com.hnpc.exchange.news.manager.dto.SyncHistory;
import com.hnpc.exchange.news.repository.dao.ArticleCatDao;
import com.hnpc.exchange.news.repository.dao.ArticleDao;
import com.hnpc.exchange.news.repository.entity.ArticleCatEntity;
import com.hnpc.exchange.news.repository.entity.ArticleEntity;
import com.hnpc.exchange.redis.configuration.RedisSetting;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * exchanger
 * Created by Damon on 10/02/2018.
 */
@EnableTransactionManagement
@Component
@Slf4j
public class NewsSyncTimerService {

    @Autowired
    private RedisSetting redisSetting;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleCatDao articleCatDao;
    @Autowired
    private NewsSyncManager newsSyncManager;
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Scheduled(fixedRate = 10000)
    public void syncNews(){
        List<ArticleCatEntity> articleCatEntityList=articleCatDao.queryAvailableColumns();
        if(CollectionUtils.isEmpty(articleCatEntityList)){
            log.info("there's no available column in system,skip sync.");
        }

        for(ArticleCatEntity articleCatEntity : articleCatEntityList){
            //查询栏目上次同步时间
            SyncHistory syncHistory=newsSyncManager.find(String.valueOf(articleCatEntity.getId()));
            //查询有新闻变化的栏目
            List<ArticleEntity> articleEntityList=articleDao.queryAllChanged(
                    String.valueOf(articleCatEntity.getId()),simpleDateFormat.format(syncHistory.getLastSyncTime()));
            if(CollectionUtils.isEmpty(articleEntityList)){
                log.info("栏目号：{}从{}到{}无变化数据",articleCatEntity.getId(),syncHistory.getLastSyncTime(),new Date());
                continue;
            }
            log.info("栏目号：{}从{}到{}有{}条变化数据",
                    articleCatEntity.getId(),syncHistory.getLastSyncTime(),new Date(),articleEntityList.size());
            int position=1;
            for(ArticleEntity articleEntity : articleEntityList){
                News news= null;
                try {
                    news = News.builder().columnid(String.valueOf(articleCatEntity.getId())).
                            newsid(articleEntity.getArticle_id()).author(articleEntity.getAuthor_name()).
                            title(articleEntity.getTitle()).
                            imagePath(articleEntity.getImage_url()).
                            accesscount(articleEntity.getAccess_count()+1).
                            publishDate(DateUtils.parseDate(articleEntity.getPublish_date(),"yyyy-MM-dd").getTime()).build();

                } catch (ParseException e) {
                   log.error("栏目号：{},新闻编号：{}, publishDate:{} is not valid date format,skip({}) sync to cache",
                           articleCatEntity.getId(),articleEntity.getId(),
                           articleEntity.getPublish_date(),position++);
                   continue;
                }
                if(articleCatEntity.getStatus()== 2){
                    news.setFlag(NewsConstantEnum.NEWS_FLAG_UPDATE.code());
                }else{
                    news.setFlag(NewsConstantEnum.NEWS_FLAG_DELETE.code());
                }
                newsSyncManager.saveCache(news);
            }
        }
    }
}
