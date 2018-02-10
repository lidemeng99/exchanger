package com.hnpc.exchange.news.manager;

/**
 * exchanger
 * Created by Damon on 09/02/2018.
 */
public enum NewsConstantEnum {
    NEWS_FLAG_CREATE(1),
    NEWS_FLAG_UPDATE(2),
    NEWS_FLAG_DELETE(-1);

    private Integer code;


    NewsConstantEnum(Integer code) {
        this.code = code;
    }

    public Integer code(){
        return this.code;
    }


    public static Integer getCode(String name) {
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
