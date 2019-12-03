package com.springboot.coordinates.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author: yangguotao
 * @Date: 2019/12/2
 * @Version 1.0
 * 经纬度格式转换
 */
public class LonAndLatFormat {
    static Logger logger = LoggerFactory.getLogger(LonAndLatFormat.class);

    /**
     * 十进制转度分秒
     *
     * @param decimalValue 经度或纬度格式值00`00"00.00000
     * @return 度分秒格式值
     */
    public static String DecimalRpm(double decimalValue) {
        String rpm = "";
        if (decimalValue > 0) {
            int degrees = (int) decimalValue;
            double minutes = (decimalValue - degrees) * 60,
                   seconds = (minutes - (int) minutes) * 60;
            minutes = MyMath.round(minutes, 8);
            seconds = MyMath.round(seconds, 8);
            String strMinutes;
            String strSeconds;
            if ((int) minutes < 10) {
                strMinutes = "0" + (int) minutes;
            } else {
                strMinutes = String.valueOf((int) minutes);
            }
            if ((int) seconds < 10) {
                strSeconds = "0" + seconds;
            } else {
                strSeconds = String.valueOf(seconds);
            }
            rpm = degrees + "°" + strMinutes + "`" + strSeconds + "\"";
        }
        logger.warn("十进制转度分秒:", LonAndLatFormat.class);
        return rpm;
    }

    /**
     * 度分秒转十进制
     *
     * @param rpm 度分秒字符串
     * @return 十进制数据
     */
    public static double RpmDecimal(String rpm) {
        double decimal = 0.0;
        if (!rpm.isEmpty()) {
            String[] remArr1 = rpm.split("°");
            if (remArr1.length > 1) {
                String[] remArr2 = remArr1[1].split("\\\\`");
                if (remArr2.length > 1) {
                    decimal = Double.valueOf(remArr1[0]) + (Double.valueOf(remArr2[0]) + Double.valueOf(remArr1[1])) / 60;
                } else {
                    decimal = Double.valueOf(remArr1[0]) + Double.valueOf(remArr2[0]);
                }
            } else {
                decimal = Double.valueOf(remArr1[0]);
            }
        }
        logger.warn("度分秒转十进制:", LonAndLatFormat.class);
        return decimal;
    }
}
