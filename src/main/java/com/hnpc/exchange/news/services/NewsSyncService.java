/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

package com.hnpc.exchange.news.services;

import com.hnpc.exchange.base.message.model.ResponseResult;
import com.hnpc.exchange.news.manager.NewsSyncManager;
import com.hnpc.exchange.news.repository.entity.ArticleEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * exchanger
 * Created by Damon on 23/02/2018.
 */
@ResponseResult
@RestController
@RequestMapping("/api/v1.0")
public class NewsSyncService {
  @Autowired
  private NewsSyncManager newsSyncManager;

  @PostMapping("/article")
  public @ResponseBody
  ArticleEntity syncArticle(@RequestBody ArticleEntity articleEntity) {
    return newsSyncManager.syncExternalArticle(articleEntity);
  }
}
