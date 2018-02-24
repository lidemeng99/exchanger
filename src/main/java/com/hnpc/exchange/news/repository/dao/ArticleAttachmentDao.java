/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

package com.hnpc.exchange.news.repository.dao;

import com.hnpc.exchange.news.repository.entity.ArticleAttachmentEntity;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * exchanger
 * Created by Damon on 24/02/2018.
 */
public interface ArticleAttachmentDao extends
    CrudRepository<ArticleAttachmentEntity, Integer>,
    JpaSpecificationExecutor<ArticleAttachmentEntity> {
}
