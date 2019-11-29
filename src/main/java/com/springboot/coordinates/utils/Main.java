package com.springboot.coordinates.utils;

public class Main {


  public static void main (String[] args) {
    randomTest ();
  }


  private static void randomTest () {
    double lon = 109.67573115833333;//经度
    double lat =34.14989814166667;//经度
    double L0 =111.00000708333333;
    double[] xy = GaussKruger.ll2xy(1, L0, lon, lat);
    double[] ll = GaussKruger.xy2ll(1, L0, xy);
    System.out.println(String.format("[%f, %f]\n\t[%f, %f]\n[%f, %f]", lon, lat, xy[0], xy[1], ll[0], ll[1]));
  }
}
