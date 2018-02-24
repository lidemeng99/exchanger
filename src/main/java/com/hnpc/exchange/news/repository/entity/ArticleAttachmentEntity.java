/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

package com.hnpc.exchange.news.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * exchanger
 * Created by Damon on 24/02/2018.
 */
@Entity
@Table(name = "PB2_ATTACHMENTS")
@Data
public class ArticleAttachmentEntity {
  @Id
  private int id;
  @Column(name = "FILE_TYPE")
  private String fileType;
  @Column(name = "NAME")
  private String name;
  @Column(name = "PATH")
  private String path;
  @Column(name = "FILE_FROM")
  private String fileFlag;
}
