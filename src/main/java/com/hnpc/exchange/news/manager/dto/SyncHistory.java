/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.news.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * exchanger
 * Created by Damon on 10/02/2018.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SyncHistory {
    private String columnId;
    private Date lastSyncTime;
    private int syncTotal;
}
