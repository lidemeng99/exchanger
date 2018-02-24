/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

package com.hnpc.exchange.news.repository.dao;

import com.hnpc.exchange.news.repository.entity.ArticleEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * exchanger
 * Created by Damon on 10/02/2018.
 */
public interface ArticleDao extends
    CrudRepository<ArticleEntity, String>, JpaSpecificationExecutor<ArticleEntity> {


  @Query("SELECT article FROM ArticleEntity article "
      + "where  article.catid=?1 "
      + "and article.lastUpdated>=TO_DATE(?2, 'YYYY-MM-DD HH24:MI:SS') ")
  List<ArticleEntity> queryAllChanged(int columnId, String lastModifyTime);
}
