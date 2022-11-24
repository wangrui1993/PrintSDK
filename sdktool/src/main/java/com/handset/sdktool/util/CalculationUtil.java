package com.handset.sdktool.util;

import java.math.BigDecimal;

public class CalculationUtil {
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double multiplication(double price, double num) {
        BigDecimal bignum1 = new BigDecimal(price);
        bignum1.setScale(3, BigDecimal.ROUND_DOWN);
        BigDecimal bignum2 = new BigDecimal(num);
        bignum2.setScale(3, BigDecimal.ROUND_DOWN);
        BigDecimal bignum3 = bignum1.multiply(bignum2);
        bignum3.setScale(3, BigDecimal.ROUND_DOWN);
        return bignum3.doubleValue();
    }

    public static double reduce(double price, double num) {
        BigDecimal p1 = new BigDecimal(Double.toString(price));
        BigDecimal p2 = new BigDecimal(Double.toString(num));
        return p1.subtract(p2).doubleValue();
    }

    public static double add(double price, double num) {
        BigDecimal p1 = new BigDecimal(Double.toString(price));
        BigDecimal p2 = new BigDecimal(Double.toString(num));
        return p1.add(p2).doubleValue();
    }
}
