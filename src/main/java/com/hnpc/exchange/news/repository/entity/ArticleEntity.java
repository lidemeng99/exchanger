/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */
package com.hnpc.exchange.news.repository.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * exchanger
 * Created by Damon on 10/02/2018.
 */
@Entity
@Table(name = "PB2_ARTICLE")
@Data
public class ArticleEntity {
    @Id
    private int id;
    private String title;
    private String subTitle;
    private String author;
    private String priority;
    private String show_way;
    private String attach_content_id;
    private String article_id;
    private String audit_date;
    private String auditor_id;
    private String auditor_name;
    private String author_name;
    private String author_id;
    private String content;
    //private String mobile_content;
    private String date_created;
    private String image_url;
    private String last_updated;
    private String main_attach_id;
    private String message_id;
    private String news_field;
    private String news_topic;
    private int access_count;
    private String publish_date;
    private String status;
    private String valid_date;
    private String valid_day;
    private String reminder_id;

    //private String publishscope;
    //private String source;//来源
    //private String sourceUnit;//发布单位

}
