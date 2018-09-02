package com.github.fujianlian.klinechart.entity;

/**
 * @author fujianlian Created on 2018/8/20 16:08
 * @descripe KDJ指标(随机指标)接口
 * 相关说明:https://baike.baidu.com/item/KDJ%E6%8C%87%E6%A0%87/6328421?fr=aladdin&fromid=3423560&fromtitle=kdj
 */
public interface IKDJ {

    /**
     * K值
     */
    float getK();

    /**
     * D值
     */
    float getD();

    /**
     * J值
     */
    float getJ();

}