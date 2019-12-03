package com.springboot.coordinates.utils;

import java.math.BigDecimal;

/**
 * 解决double除法精度问题
 *
 * @Author: yangguotao
 * @Date: 2019/12/3
 * @Version 1.0
 */
public class MyMath {


    public static final int DEF_DIV_SCALE = 10;//除法精确到小数点后位数

    private MyMath() {
    }

    /**
     * double加法运算
     *
     * @param e1
     * @param e2
     * @return
     */
    public static  double add(double e1,double e2){
       BigDecimal b1  =  new BigDecimal(Double.toString(e1));
       BigDecimal b2  =  new BigDecimal(Double.toString(e2));
       return  b1.add(b2).doubleValue();
    }

    /**
     * 减法运算
     *
     * @param e1
     * @param e2
     * @return
     */
    public static  double sub(double e1,double e2){
        BigDecimal b1  =  new BigDecimal(Double.toString(e1));
        BigDecimal b2  =  new BigDecimal(Double.toString(e2));
        return  b1.subtract(b2).doubleValue();
    }

    /**
     * 乘法运算
     * @param e1
     * @param e2
     * @return
     */
    public static  double mul(double e1,double e2){
        BigDecimal b1  =  new BigDecimal(Double.toString(e1));
        BigDecimal b2  =  new BigDecimal(Double.toString(e2));
        return  b1.multiply(b2).doubleValue();
    }

    /**
     * 除法运算精确精确到少数点后#DEF_DIV_SCALE#位，
     * 之后四舍五入
     * @param e1
     * @param e2
     * @return
     */
    public static  double div(double e1,double e2){
        return  div( e1, e2, DEF_DIV_SCALE);
    }

    /**
     * 除法运算精确精确到少数点后指定位之后四舍五入
     * @param e1
     * @param e2
     * @param scale
     * @return
     */
    public static  double div(double e1,double e2,int scale){
       if(scale<0){
          throw  new IllegalArgumentException("The scale must be a positive integer or zero");
       }
        BigDecimal b1  =  new BigDecimal(Double.toString(e1));
        BigDecimal b2  =  new BigDecimal(Double.toString(e2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 提供精确的小数位四舍五入处理
     * @param e 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double e,int scale){

        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(e));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


}
