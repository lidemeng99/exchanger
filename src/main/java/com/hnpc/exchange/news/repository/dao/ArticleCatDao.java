/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

package com.hnpc.exchange.news.repository.dao;

import com.hnpc.exchange.news.repository.entity.ArticleCatEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * exchanger
 * Created by Damon on 10/02/2018.
 */
public interface ArticleCatDao extends CrudRepository<ArticleCatEntity, Integer>,
    JpaSpecificationExecutor<ArticleCatEntity> {

  @Query("SELECT articlecat FROM ArticleCatEntity articlecat WHERE articlecat.status=2")
  List<ArticleCatEntity> queryAvailableColumns();

}
