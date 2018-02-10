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
@Table(name = "PB2_ARTICLECAT")
@Data
public class ArticleCatEntity {
    @Id
    private int id;
    private String name;
    private int parentId;
    private int status;
}
