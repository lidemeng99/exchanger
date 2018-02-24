/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

package com.hnpc.exchange.news.repository.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;


/**
 * exchanger
 * Created by Damon on 10/02/2018.
 */
@Entity
@Table(name = "PB2_ARTICLE")
@SequenceGenerator(name = "SEQ_SYNC_SEQUENCE", sequenceName = "SEQ_SYNC_SEQUENCE")
@Data
public class ArticleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SYNC_SEQUENCE")
  @Column(name = "ID")
  private int newsid;
  @Column(name = "TITLE", length = 2000)
  private String title;
  @Column(name = "AUTHOR")
  private String author;
  @Column(name = "AUTHOR_NAME")
  private String authorname;
  @Column(name = "AUTHOR_ID")
  private String authorid;
  @Column(name = "PUBLISH_DATE")
  private Date publishDate;
  @Column(name = "PRIORITY")
  private String priority;
  @Column(name = "MOBILE_CONTENT")
  private String text;
  @Column(name = "DATE_CREATED")
  private Date createdDate;
  @Column(name = "ACCESS_COUNT")
  private int accessCount;
  @Column(name = "LAST_UPDATED")
  private Date lastUpdated;
  @Column(name = "STATUS")
  private int status;
  @Column(name = "CAT_ID")
  private int catid;
  @Column(name = "IMAGE_URL")
  private String imageUrl;
  @Column(name = "MAIN_ATTACH_ID")
  private int mainAttachId;
  @Column(name = "VIDEO_IMG_ID")
  private int videoThumbnailId;
  @Column(name = "SOURCE")
  private String source;
  @Column(name = "SOURCE_UNIT")
  private String sourceUnit;
  @Column(name = "VERSION")
  private int version = 0;

  @Transient
  private ArticleAttachmentEntity mainThumbnail;
  @Transient
  private ArticleAttachmentEntity videoThumbnail;

}
