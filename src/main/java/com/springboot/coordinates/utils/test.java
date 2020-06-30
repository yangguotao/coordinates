package com.springboot.coordinates.utils;

import java.math.BigDecimal;

public class test {


    public static void main(String[] args) {
        randomTest();
    }


    private static void randomTest() {
        //经度
        double lon = 89.244005472;
        //经度
        double lat = 44.826124122;
        double L0 = 90;
        double[] xy = GaussKruger.ll2xy(1, L0, lon, lat);
        double[] ll = GaussKruger.xy2ll(1, L0, xy);
        System.out.println(String.format("[%.7f, %.7f]\n\t[%.7f, %.7f]\n[%.7f, %.7f]", lon, lat, xy[0], xy[1], ll[0], ll[1]));
    }
}
