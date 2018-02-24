/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

package com.hnpc.exchange.news.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnpc.exchange.base.configuration.SystemFormatSetting;
import com.hnpc.exchange.base.exception.param.BlankParameterException;
import com.hnpc.exchange.base.exception.param.TypeBindErrorParameterException;
import com.hnpc.exchange.news.manager.dto.News;
import com.hnpc.exchange.news.manager.dto.SyncHistory;
import com.hnpc.exchange.news.repository.dao.ArticleAttachmentDao;
import com.hnpc.exchange.news.repository.dao.ArticleDao;
import com.hnpc.exchange.news.repository.entity.ArticleAttachmentEntity;
import com.hnpc.exchange.news.repository.entity.ArticleCatEntity;
import com.hnpc.exchange.news.repository.entity.ArticleEntity;
import com.hnpc.exchange.redis.configuration.RedisSetting;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * exchanger
 * Created by Damon on 09/02/2018.
 */
@Component
@Slf4j
public class NewsSyncManager {
  @Autowired
  private RedisTemplate<String, String> jsonObjectRedisTemplate;
  @Autowired
  private RedisSetting redisSetting;
  @Autowired
  private ArticleDao articleDao;
  @Autowired
  private ArticleAttachmentDao articleAttachmentDao;
  @Autowired
  private ColumnQueryManager columnQueryManager;
  @Autowired
  private SystemFormatSetting systemFormatSetting;

  private ObjectMapper mapper = new ObjectMapper();


  /**
   * 保存新闻对象至缓存.
   *
   * @param news {@link News}
   * @return 从缓存中获取到的缓存
   */
  public News saveCache(News news) {
    if (news == null) {
      throw new BlankParameterException("传入参数{}为空", News.class.getName());
    }
    String key = news.getColumnid() + redisSetting.getKeysplitter() + news.getNewsid();

    try {
      news.setLastModifytime(new Date().getTime());
      //如果KEY已经存在，直接覆盖
      if (news.getFlag().equals(NewsConstantEnum.NEWS_FLAG_DELETE.code())) {
        jsonObjectRedisTemplate.delete(key);
        log.info("DELETE key:{} success to cache", key);
      } else if (news.getFlag().equals(NewsConstantEnum.NEWS_FLAG_CREATE.code())
          || news.getFlag().equals(NewsConstantEnum.NEWS_FLAG_UPDATE.code())) {
        String newsJsonStr = mapper.writeValueAsString(news);
        jsonObjectRedisTemplate.opsForValue().set(key, newsJsonStr);
        log.info("SAVE key:{} success to cache", key);
      } else {
        throw new TypeBindErrorParameterException("News.Flag can not set correct");
      }

    } catch (JsonProcessingException e) {
      log.info("save key:{} failure to cache", key);
      throw new TypeBindErrorParameterException("传入的对象为不合法JSON格式");
    }
    return news;
  }

  /**
   * 查找某一栏目的数据同步历史.
   *
   * @param columnId 栏目号
   * @return {@link SyncHistory}
   */
  public SyncHistory find(String columnId) {
    if (StringUtils.isBlank(columnId)) {
      throw new BlankParameterException("参数中栏目号为空");
    }
    String key = "SyncHis:" + columnId;
    String value = jsonObjectRedisTemplate.opsForValue().get(key);
    if (StringUtils.isBlank(value)) {

      return new SyncHistory(columnId, new Date(NumberUtils.LONG_ZERO), NumberUtils.INTEGER_ZERO);

    }
    try {
      return mapper.readValue(value, SyncHistory.class);
    } catch (IOException e) {
      throw new TypeBindErrorParameterException("传入的对象为不合法JSON格式");
    }
  }

  /**
   * 存储栏目数据同步历史记录.
   *
   * @param syncHistory {@link SyncHistory}
   * @return {@link SyncHistory}
   */
  public SyncHistory saveSyncHistory(SyncHistory syncHistory) {
    if (syncHistory == null) {
      throw new BlankParameterException("传入参数{}为空", News.class.getName());
    }
    if (StringUtils.isBlank(syncHistory.getColumnId())) {
      throw new BlankParameterException("参数中栏目号为空");
    }
    String key = "SyncHis:" + syncHistory.getColumnId();

    try {
      String syncHistoryJsonStr = mapper.writeValueAsString(syncHistory);
      jsonObjectRedisTemplate.opsForValue().set(key, syncHistoryJsonStr);
      log.info("save key:{} success to cache", key);
    } catch (JsonProcessingException e) {
      log.info("save key:{} failure to cache", key);
      throw new TypeBindErrorParameterException("传入的对象为不合法JSON格式");
    }

    return syncHistory;
  }

  /**
   * 从外部系统同步新闻列表.
   *
   * @param articleEntity List {@link ArticleEntity}
   * @return Map(1/0, List) {@link ArticleEntity}
   */

  public ArticleEntity syncExternalArticle(
      ArticleEntity articleEntity) {
    String syncMessageid = UUID.randomUUID().toString();
    if (articleEntity == null) {
      log.error("MessageID:{}-There's no any articles synced from external", syncMessageid);
      throw new BlankParameterException("no any articles synced from external");
    }

    articleEntity = articleDao.save(articleEntity);
    log.info("MessageID:{}-succeed to save article-{}", syncMessageid, articleEntity.getNewsid());
    return articleEntity;
  }

  /**
   * 查询指定栏目下面所有变化的本新闻.
   *
   * @param columnid       栏目号
   * @param lastChangeTime 上次更新时间
   * @return List {@link ArticleEntity}
   */
  public List<ArticleEntity> queryAllChangedArticles(int columnid, String lastChangeTime) {
    List<ArticleEntity> changedArticles = articleDao.queryAllChanged(columnid, lastChangeTime);
    if (CollectionUtils.isEmpty(changedArticles)) {
      return changedArticles;
    }
    for (ArticleEntity article : changedArticles) {
      if (article.getMainAttachId() != 0) {
        article.setMainThumbnail(articleAttachmentDao.findOne(article.getMainAttachId()));
      } else if (article.getVideoThumbnailId() != 0) {
        article.setVideoThumbnail(articleAttachmentDao.findOne(article.getVideoThumbnailId()));
      }
    }
    return changedArticles;
  }

  /**
   * 按照栏目增量同步所有变化的新闻数据.
   */
  public void syncNewsByColumns() {
    List<ArticleCatEntity> articleCatEntityList = columnQueryManager.findAllAvailableColumns();
    if (CollectionUtils.isEmpty(articleCatEntityList)) {
      log.info("there's no available column in system,skip sync.");
    }

    for (ArticleCatEntity articleCatEntity : articleCatEntityList) {
      //查询栏目上次同步时间
      SyncHistory syncHistory = find(String.valueOf(articleCatEntity.getId()));
      String lastUpdateTime = DateFormatUtils.format(syncHistory.getLastSyncTime(),
          systemFormatSetting.getDate_format());

      //查询有新闻变化的栏目
      List<ArticleEntity> articleEntityList = articleDao.queryAllChanged(articleCatEntity.getId(),
          lastUpdateTime);

      if (CollectionUtils.isEmpty(articleEntityList)) {
        log.info("栏目号：{}从{}后无变化数据", articleCatEntity.getId(),
            lastUpdateTime);
        continue;
      }

      log.info("栏目号：{}从{}后有{}条变化数据", articleCatEntity.getId(), lastUpdateTime,
          articleEntityList.size());


      int position = 1;
      for (ArticleEntity articleEntity : articleEntityList) {
        News news = null;
        try {
          news = News.builder().columnid(articleCatEntity.getId())
              .newsid(articleEntity.getNewsid())
              .author(articleEntity.getAuthor())
              .title(articleEntity.getTitle())
              .imagePath(articleEntity.getImageUrl())
              .accesscount(articleEntity.getAccessCount() + 1)
              .priority(articleEntity.getPriority())
              .text(articleEntity.getText())
              .source(articleEntity.getSource())
              .sourceUnit(articleEntity.getSourceUnit())
              .publishDate(articleEntity.getPublishDate().getTime()).build();
          ArticleAttachmentEntity mainThumbnail = articleEntity.getMainThumbnail();
          ArticleAttachmentEntity videoThumbnail = articleEntity.getVideoThumbnail();
          if (mainThumbnail != null && mainThumbnail.getFileFlag()
              .equals(NewsConstantEnum.NEWS_FLAG_MAIN_ATTACHMENT.code())) {
            news.setImagePath(mainThumbnail.getPath());
          } else if (videoThumbnail != null) {
            news.setVideoThumbnail(videoThumbnail.getPath());
          }


        } catch (Exception e) {
          log.error("栏目号：{},新闻编号：{}, publishDate:{} "
                  + "is not valid date format,skip({}) sync to cache",
              articleCatEntity.getId(), articleEntity.getNewsid(),
              articleEntity.getPublishDate(), position++);
          continue;
        }
        if (articleCatEntity.getStatus() == 2) {
          news.setFlag(NewsConstantEnum.NEWS_FLAG_UPDATE.code());
        } else {
          news.setFlag(NewsConstantEnum.NEWS_FLAG_DELETE.code());
        }
        saveCache(news);
      }
      syncHistory.setSyncTotal(articleEntityList.size() - position + 1);
      syncHistory.setLastSyncTime(new Date());
      try {

        saveSyncHistory(syncHistory);
      } catch (Exception e) {
        log.error("栏目号：{}缓存同步历史错误", articleCatEntity.getId(), e);
      }

    }
  }
}
