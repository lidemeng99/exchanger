/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.news.repository.dao;

import com.hnpc.exchange.news.repository.entity.ArticleCatEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * exchanger
 * Created by Damon on 10/02/2018.
 */
public interface ArticleCatDao extends CrudRepository<ArticleCatEntity, String>,JpaSpecificationExecutor<ArticleCatEntity> {

    @Query("SELECT articlecat FROM ArticleCatEntity articlecat WHERE articlecat.status=2")
    List<ArticleCatEntity> queryAvailableColumns();
}
