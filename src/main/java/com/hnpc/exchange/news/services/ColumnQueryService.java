/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

package com.hnpc.exchange.news.services;

import com.hnpc.exchange.base.message.model.ResponseResult;
import com.hnpc.exchange.news.manager.ColumnQueryManager;
import com.hnpc.exchange.news.repository.entity.ArticleCatEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * exchanger
 * Created by Damon on 21/02/2018.
 */
@ResponseResult
@RestController
@RequestMapping("/api/v1.0/column")
public class ColumnQueryService {
  @Autowired
  private ColumnQueryManager columnQueryManager;

  @GetMapping
  public @ResponseBody
  List<ArticleCatEntity> queryAllAvailableColumns() {
    return columnQueryManager.findAllAvailableColumns();
  }

  @GetMapping("/subscribed")
  public @ResponseBody
  List<ArticleCatEntity> querySubscribedColumns(@RequestParam(name = "subscriber") String userid) {
    return columnQueryManager.findSubscribedColumns(userid);
  }
}
