package com.github.fujianlian.klinechart.entity;

/**
 * 蜡烛图实体接口
 * Created by tifezh on 2016/6/9.
 */
public interface ICandle {

    /**
     * 开盘价
     */
    float getOpenPrice();

    /**
     * 最高价
     */
    float getHighPrice();

    /**
     * 最低价
     */
    float getLowPrice();

    /**
     * 收盘价
     */
    float getClosePrice();


    // 以下为MA数据
    /**
     * 五(月，日，时，分，5分等)均价
     */
    float getMA5Price();

    /**
     * 十(月，日，时，分，5分等)均价
     */
    float getMA10Price();

    /**
     * 二十(月，日，时，分，5分等)均价
     */
    float getMA20Price();

    /**
     * 三十(月，日，时，分，5分等)均价
     */
    float getMA30Price();

    /**
     * 六十(月，日，时，分，5分等)均价
     */
    float getMA60Price();

    // 以下为BOLL数据
    /**
     * 上轨线
     */
    float getUp();

    /**
     * 中轨线
     */
    float getMb();

    /**
     * 下轨线
     */
    float getDn();

}
