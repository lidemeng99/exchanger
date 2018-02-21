/*
 *
 * Copyright 2016 IBM or CNNP.
 * 
 */

package com.hnpc.exchange.news.repository.dao;

import com.hnpc.exchange.news.repository.entity.ProfileEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * exchanger
 * Created by Damon on 21/02/2018.
 */
public interface ProfileDao extends
    CrudRepository<ProfileEntity, String>, JpaSpecificationExecutor<ProfileEntity> {

  @Query("select profile from ProfileEntity profile where profile.userid=?1 and profile.status=1")
  List<ProfileEntity> queryByUser(String userid);
}
