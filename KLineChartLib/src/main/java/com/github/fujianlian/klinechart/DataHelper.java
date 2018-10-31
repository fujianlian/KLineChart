package com.github.fujianlian.klinechart;

import java.util.List;

/**
 * 数据辅助类 计算macd rsi等
 * Created by tifezh on 2016/11/26.
 */
public class DataHelper {

    /**
     * 计算RSI
     *
     * @param dataList
     */
    static void calculateRSI(List<KLineEntity> dataList) {
        Float rsi;
        float rsiABSEma = 0;
        float rsiMaxEma = 0;
        for (int i = 0; i < dataList.size(); i++) {
            KLineEntity point = dataList.get(i);
            final float closePrice = point.getClosePrice();
            if (i == 0) {
                rsi = 0f;
                rsiABSEma = 0;
                rsiMaxEma = 0;
            } else {
                float Rmax = Math.max(0, closePrice - dataList.get(i - 1).getClosePrice());
                float RAbs = Math.abs(closePrice - dataList.get(i - 1).getClosePrice());

                rsiMaxEma = (Rmax + (14f - 1) * rsiMaxEma) / 14f;
                rsiABSEma = (RAbs + (14f - 1) * rsiABSEma) / 14f;
                rsi = (rsiMaxEma / rsiABSEma) * 100;
            }
            if (i < 13) {
                rsi = 0f;
            }
            if (rsi.isNaN())
                rsi = 0f;
            point.rsi = rsi;
        }
    }

    /**
     * 计算kdj
     *
     * @param dataList
     */
    static void calculateKDJ(List<KLineEntity> dataList) {
        float k = 0;
        float d = 0;
        for (int i = 0; i < dataList.size(); i++) {
            KLineEntity point = dataList.get(i);
            final float closePrice = point.getClosePrice();
            int startIndex = i - 13;
            if (startIndex < 0) {
                startIndex = 0;
            }
            float max14 = Float.MIN_VALUE;
            float min14 = Float.MAX_VALUE;
            for (int index = startIndex; index <= i; index++) {
                max14 = Math.max(max14, dataList.get(index).getHighPrice());
                min14 = Math.min(min14, dataList.get(index).getLowPrice());
            }
            Float rsv = 100f * (closePrice - min14) / (max14 - min14);
            if (rsv.isNaN()) {
                rsv = 0f;
            }
            if (i == 0) {
                k = 50;
                d = 50;
            } else {
                k = (rsv + 2f * k) / 3f;
                d = (k + 2f * d) / 3f;
            }
            if (i < 13) {
                point.k = 0;
                point.d = 0;
                point.j = 0;
            } else if (i == 13 || i == 14) {
                point.k = k;
                point.d = 0;
                point.j = 0;
            } else {
                point.k = k;
                point.d = d;
                point.j = 3f * k - 2 * d;
            }
        }

    }

    /**
     * 计算wr
     *
     * @param dataList
     */
    static void calculateWR(List<KLineEntity> dataList) {
        Float r;
        for (int i = 0; i < dataList.size(); i++) {
            KLineEntity point = dataList.get(i);
            int startIndex = i - 14;
            if (startIndex < 0) {
                startIndex = 0;
            }
            float max14 = Float.MIN_VALUE;
            float min14 = Float.MAX_VALUE;
            for (int index = startIndex; index <= i; index++) {
                max14 = Math.max(max14, dataList.get(index).getHighPrice());
                min14 = Math.min(min14, dataList.get(index).getLowPrice());
            }
            if (i < 13) {
                point.r = -10;
            } else {
                r = -100 * (max14 - dataList.get(i).getClosePrice()) / (max14 - min14);
                if (r.isNaN()) {
                    point.r = 0;
                } else {
                    point.r = r;
                }
            }
        }

    }

    /**
     * 计算macd
     *
     * @param dataList
     */
    static void calculateMACD(List<KLineEntity> dataList) {
        float ema12 = 0;
        float ema26 = 0;
        float dif = 0;
        float dea = 0;
        float macd = 0;

        for (int i = 0; i < dataList.size(); i++) {
            KLineEntity point = dataList.get(i);
            final float closePrice = point.getClosePrice();
            if (i == 0) {
                ema12 = closePrice;
                ema26 = closePrice;
            } else {
                // EMA（12） = 前一日EMA（12） X 11/13 + 今日收盘价 X 2/13
                ema12 = ema12 * 11f / 13f + closePrice * 2f / 13f;
                // EMA（26） = 前一日EMA（26） X 25/27 + 今日收盘价 X 2/27
                ema26 = ema26 * 25f / 27f + closePrice * 2f / 27f;
            }
            // DIF = EMA（12） - EMA（26） 。
            // 今日DEA = （前一日DEA X 8/10 + 今日DIF X 2/10）
            // 用（DIF-DEA）*2即为MACD柱状图。
            dif = ema12 - ema26;
            dea = dea * 8f / 10f + dif * 2f / 10f;
            macd = (dif - dea) * 2f;
            point.dif = dif;
            point.dea = dea;
            point.macd = macd;
        }

    }

    /**
     * 计算 BOLL 需要在计算ma之后进行
     *
     * @param dataList
     */
    static void calculateBOLL(List<KLineEntity> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            KLineEntity point = dataList.get(i);
            if (i < 19) {
                point.mb = 0;
                point.up = 0;
                point.dn = 0;
            } else {
                int n = 20;
                float md = 0;
                for (int j = i - n + 1; j <= i; j++) {
                    float c = dataList.get(j).getClosePrice();
                    float m = point.getMA20Price();
                    float value = c - m;
                    md += value * value;
                }
                md = md / (n - 1);
                md = (float) Math.sqrt(md);
                point.mb = point.getMA20Price();
                point.up = point.mb + 2f * md;
                point.dn = point.mb - 2f * md;
            }
        }

    }

    /**
     * 计算ma
     *
     * @param dataList
     */
    static void calculateMA(List<KLineEntity> dataList) {
        float ma5 = 0;
        float ma10 = 0;
        float ma20 = 0;
        float ma30 = 0;
        float ma60 = 0;

        for (int i = 0; i < dataList.size(); i++) {
            KLineEntity point = dataList.get(i);
            final float closePrice = point.getClosePrice();

            ma5 += closePrice;
            ma10 += closePrice;
            ma20 += closePrice;
            ma30 += closePrice;
            ma60 += closePrice;
            if (i == 4) {
                point.MA5Price = ma5 / 5f;
            } else if (i >= 5) {
                ma5 -= dataList.get(i - 5).getClosePrice();
                point.MA5Price = ma5 / 5f;
            } else {
                point.MA5Price = 0f;
            }
            if (i == 9) {
                point.MA10Price = ma10 / 10f;
            } else if (i >= 10) {
                ma10 -= dataList.get(i - 10).getClosePrice();
                point.MA10Price = ma10 / 10f;
            } else {
                point.MA10Price = 0f;
            }
            if (i == 19) {
                point.MA20Price = ma20 / 20f;
            } else if (i >= 20) {
                ma20 -= dataList.get(i - 20).getClosePrice();
                point.MA20Price = ma20 / 20f;
            } else {
                point.MA20Price = 0f;
            }
            if (i == 29) {
                point.MA30Price = ma30 / 30f;
            } else if (i >= 30) {
                ma30 -= dataList.get(i - 30).getClosePrice();
                point.MA30Price = ma30 / 30f;
            } else {
                point.MA30Price = 0f;
            }
            if (i == 59) {
                point.MA60Price = ma60 / 60f;
            } else if (i >= 60) {
                ma60 -= dataList.get(i - 60).getClosePrice();
                point.MA60Price = ma60 / 60f;
            } else {
                point.MA60Price = 0f;
            }
        }
    }

    /**
     * 计算MA BOLL RSI KDJ MACD
     *
     * @param dataList
     */
    public static void calculate(List<KLineEntity> dataList) {
        calculateMA(dataList);
        calculateMACD(dataList);
        calculateBOLL(dataList);
        calculateRSI(dataList);
        calculateKDJ(dataList);
        calculateWR(dataList);
        calculateVolumeMA(dataList);
    }

    private static void calculateVolumeMA(List<KLineEntity> entries) {
        float volumeMa5 = 0;
        float volumeMa10 = 0;

        for (int i = 0; i < entries.size(); i++) {
            KLineEntity entry = entries.get(i);

            volumeMa5 += entry.getVolume();
            volumeMa10 += entry.getVolume();

            if (i == 4) {
                entry.MA5Volume = (volumeMa5 / 5f);
            } else if (i > 4) {
                volumeMa5 -= entries.get(i - 5).getVolume();
                entry.MA5Volume = volumeMa5 / 5f;
            } else {
                entry.MA5Volume = 0f;
            }

            if (i == 9) {
                entry.MA10Volume = volumeMa10 / 10f;
            } else if (i > 9) {
                volumeMa10 -= entries.get(i - 10).getVolume();
                entry.MA10Volume = volumeMa10 / 10f;
            } else {
                entry.MA10Volume = 0f;
            }
        }
    }
}
