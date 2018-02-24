/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

package com.hnpc.exchange.news.services;

import com.hnpc.exchange.news.manager.NewsSyncManager;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * exchanger
 * Created by Damon on 10/02/2018.
 */

@Component
@Slf4j
public class NewsSyncTimerService {

  @Autowired
  private NewsSyncManager newsSyncManager;

  /**
   * 每10S同步数据变化数据.
   */
  @Scheduled(fixedRate = 10000)
  public void syncNews() {
    newsSyncManager.syncNewsByColumns();
  }
}
