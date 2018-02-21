/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

package com.hnpc.exchange.news.services;

import com.hnpc.exchange.base.message.model.ResponseResult;
import com.hnpc.exchange.news.manager.NewsQueryManager;
import com.hnpc.exchange.news.manager.dto.News;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * exchanger
 * Created by Damon on 09/02/2018.
 */
@ResponseResult
@RestController
@RequestMapping("/api/v1.0/news")
public class NewsQueryService {
  @Autowired
  private NewsQueryManager newsQueryManager;

  @GetMapping("")
  public @ResponseBody
  List<News> queryCacheNewsByColumnId(@RequestParam("column_id") String columnId) {
    return newsQueryManager.queryFromCacheByColumn(columnId);
  }
}
