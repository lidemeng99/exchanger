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

/**
 * exchanger
 * Created by Damon on 08/02/2018.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class News {
    private String newsid;
    private String columnid;
    private String title;
    private String author;
    private long publishDate;
    private String priority;
    private String imagePath;
    private int accesscount;
    private String source;
    private String sourceUnit;
    private String videoThumbnail;
    private String text;
    private boolean latest;
    private String domain;
    private long lastModifytime;
    private int flag;
}
