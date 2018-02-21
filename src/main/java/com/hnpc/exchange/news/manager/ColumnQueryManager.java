/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

package com.hnpc.exchange.news.manager;

import com.hnpc.exchange.base.exception.param.BlankParameterException;
import com.hnpc.exchange.news.repository.dao.ArticleCatDao;
import com.hnpc.exchange.news.repository.dao.ProfileDao;
import com.hnpc.exchange.news.repository.entity.ArticleCatEntity;
import com.hnpc.exchange.news.repository.entity.ProfileEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * exchanger
 * Created by Damon on 21/02/2018.
 */
@Component
@EnableTransactionManagement
@Slf4j
public class ColumnQueryManager {
  @Autowired
  private ArticleCatDao articleCatDao;
  @Autowired
  private ProfileDao profileDao;

  /**
   * 查询系统中所有有效的新闻栏目.
   *
   * @return List {@link ArticleCatEntity}
   */
  public List<ArticleCatEntity> findAllAvailableColumns() {

    return articleCatDao.queryAvailableColumns();
  }

  /**
   * 查询用户订阅的新闻栏目列表.
   *
   * @param userid 用户员工号
   * @return List {@link ArticleCatEntity}
   */
  public List<ArticleCatEntity> findSubscribedColumns(String userid) {
    if (StringUtils.isBlank(userid)) {
      throw new BlankParameterException("员工号查询参数为空");
    }
    List<ProfileEntity> profileEntities = profileDao.queryByUser(userid);
    if (CollectionUtils.isEmpty(profileEntities)) {
      log.info("员工:{}尚未订阅任何栏目.", userid);
      return ListUtils.EMPTY_LIST;
    }
    log.info("员工:{}共订阅{}个栏目.", userid, profileEntities.size());
    List<ArticleCatEntity> subscribedCatEntities = new ArrayList<ArticleCatEntity>();
    for (ProfileEntity profileEntity : profileEntities) {
      ArticleCatEntity articleCatEntity = articleCatDao.findOne(
          Integer.valueOf(profileEntity.getColumnid()));
      if (articleCatEntity == null || articleCatEntity.getStatus() == -1) {
        log.info("员工:{}订阅的{}栏目为无效栏目.", userid, profileEntity.getColumnid());
        continue;
      }
      subscribedCatEntities.add(articleCatEntity);

    }
    return subscribedCatEntities;

  }

  /**
   * 用户订阅栏目,当新订阅时，更新人可以为空，系统自动设置为订阅人.
   * 如果填写了更新人，系统将设置填写的更新人.
   *
   * @param profileEntity {@link ProfileEntity}
   * @return {@link ProfileEntity}
   */
  public ProfileEntity subscribeColumn(ProfileEntity profileEntity) {
    if (profileEntity == null || StringUtils.isBlank(profileEntity.getColumnid())
        || StringUtils.isBlank(profileEntity.getUserid())) {
      throw new BlankParameterException("传入参数为空，请检查参数中栏目号、订阅人信息.");
    }

    if (StringUtils.isBlank(profileEntity.getModifieduserid())) {
      //如果更新人信息为空，则取值为订阅人
      profileEntity.setModifieduserid(profileEntity.getUserid());
    }
    profileEntity.setModifiedtime(new Date());
    profileEntity = profileDao.save(profileEntity);
    log.info("用户:{}于{}成功订阅了栏目{}.", profileEntity.getUserid(),
        profileEntity.getModifiedtime());
    return profileEntity;
  }
}
