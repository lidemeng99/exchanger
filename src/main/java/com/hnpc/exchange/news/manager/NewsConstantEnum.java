package com.hnpc.exchange.news.manager;

/**
 * exchanger
 * Created by Damon on 09/02/2018.
 */
public enum NewsConstantEnum {
  NEWS_FLAG_CREATE("1"),
  NEWS_FLAG_UPDATE("2"),
  NEWS_FLAG_DELETE("-1"),
  NEWS_FLAG_MAIN_ATTACHMENT("主附件");

  private String code;


  NewsConstantEnum(String code) {
    this.code = code;
  }

  public String code() {
    return this.code;
  }


  public static String getCode(String name) {
    for (NewsConstantEnum item : NewsConstantEnum.values()) {
      if (item.name().equals(name)) {
        return item.code;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return this.name();
  }
}
