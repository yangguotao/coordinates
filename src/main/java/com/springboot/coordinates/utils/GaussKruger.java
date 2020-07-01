package com.springboot.coordinates.utils;

public class GaussKruger {
    /*经度偏离常数*/
    public static final double E0 = 500000,
    //纬度偏离常熟
    N0 = 0,
    //6378140;椭球长半径
    a = 6378137.0,
    //f = Math_Double.div(1,298.257223563),//椭球扁率 wgs84
    //b = a*(1-f),//6356755;椭球短半径
    //椭球扁率 wgs84
    f = Math_Double.div(1, 298.257222101),
    //6356752.31414;椭球短半径
    b = a * (1 - f),
    //椭球第一偏心率平方
    e1 = 1 - Math_Double.div(b, a) * Math_Double.div(b, a),
    //椭球第二偏心率平方
    e2 = Math_Double.div(a, b) * Math_Double.div(a, b) - 1,
    //  e1 = 1 - (b / a) * (b / a),//椭球第一偏心率平方
    // e2 = (a / b) * (a / b) - 1,//椭球第二偏心率平方
    //正算常量系数k1
    k1 = 1 - 0.25 * e1 - Math_Double.div(3 * e1 * e1, 64) - Math_Double.div(5 * e1 * e1 * e1, 256),
    //正算常量系数k2
    k2 = -Math_Double.div(3 * e1, 8) - Math_Double.div(3 * e1 * e1, 32) - Math_Double.div(45 * e1 * e1 * e1, 1024),
    //正算常量系数k3
    k3 = Math_Double.div(15 * e1 * e1, 256) + Math_Double.div(45 * e1 * e1 * e1, 1024),
    //正算常量系数k4
    k4 = -Math_Double.div(35 * e1 * e1 * e1, 3072),
            _e = Math_Double.div(a - b, a + b),
            _e2 = _e * _e,
            _e3 = _e2 * _e,
            _e4 = _e2 * _e2,
            _k1 = 1.5 * _e - Math_Double.div(7 * _e3, 32),
            _k2 = Math_Double.div(21 * _e2, 16) - Math_Double.div(55 * _e4, 32),
            _k3 = Math_Double.div(151 * _e3, 96),
            _k4 = Math_Double.div(1097 * _e4, 512);

    /**
     * 经纬度转空间坐标
     *
     * @param k0 投影比例因子
     * @param l0 中央子午线经度
     * @param ll 经纬度
     * @return UTM坐标
     */
    public static double[] ll2xy(double k0, double l0, Double... ll) {
        //经度差转弧度
        double l = Math.toRadians(ll[0] - l0);
        //纬度转弧度
        double b = Math.toRadians(ll[1]);
        double sinB = Math.sin(b);
        double sin2B = Math.sin(2 * b);
        double sin4B = Math.sin(4 * b);
        double sin6B = Math.sin(6 * b);
        double cosB = Math.cos(b);
        double tanB = Math.tan(b);
        double T = tanB * tanB;
        double T2 = T * T;
        double C = e2 * cosB * cosB;
        double C2 = C * C;
        double A = l * cosB;
        double A2 = A * A;
        double A3 = A2 * A;
        double A4 = A2 * A2;
        double A5 = A3 * A2;
        double A6 = A3 * A3;
        //经度偏移量
        double N = a / Math.sqrt(1 - e1 * sinB * sinB);
        //纬度偏移量
        double M = a * (k1 * b + k2 * sin2B + k3 * sin4B + k4 * sin6B);
        double x = N0 + k0 * (M + N * tanB * (0.5 * A2 + (5 - T + 9 * C + 4 * C2) * A4 / 24) + (61 - 58 * T + T2 + 600 * C - 300 * e2) * A6 / 720);//纬度
        double y = E0 + k0 * N * (A + Math_Double.div((1 - T + C) * A3, 6) + Math_Double.div((5 - 18 * T + T2 + 72 * C - 58 * e2) * A5, 120));//经度
        return new double[]{x, y};
    }


    /**
     * 空间坐标转化成经纬度
     *
     * @param k0 投影比例因子
     * @param l0 中央子午线经度
     * @param xy UTM坐标
     * @return 经纬度
     */
    public static double[] xy2ll(double k0, double l0, double... xy) {
        double Mf = Math_Double.div(xy[0] - N0, k0),
                B1 = Math_Double.div(Mf, a * k1),
                sin2B1 = Math.sin(2 * B1),
                sin4B1 = Math.sin(4 * B1),
                sin6B1 = Math.sin(6 * B1),
                sin8B1 = Math.sin(8 * B1),
                Bf = B1 + _k1 * sin2B1 + _k2 * sin4B1 + _k3 * sin6B1 + _k4 * sin8B1,
                sinBf = Math.sin(Bf),
                cosBf = Math.cos(Bf),
                tanBf = Math.tan(Bf),
                Nf = a / Math.sqrt(1 - e1 * sinBf * sinBf),
                Tf = tanBf * tanBf,
                Tf2 = Tf * Tf,
                Cf = e2 * cosBf * cosBf,
                Cf2 = Cf * Cf,
                _Rf = 1 - e1 * sinBf * sinBf,
                Rf = Math_Double.div(a * (1 - e1), Math.sqrt(_Rf * _Rf * _Rf)),
                D = Math_Double.div(xy[1] - E0, Nf * k0),
                D2 = D * D,
                D3 = D2 * D,
                D4 = D2 * D2,
                D5 = D3 * D2,
                D6 = D3 * D3,
                l = Math_Double.div(
                        (D - Math_Double.div((1 + 2 * Tf + Cf) * D3, 6)
                                +
                                Math_Double.div((5 - 2 * Cf + 28 * Tf - 3 * Cf2 + 8 * e2 + 24 * Tf2) * D5, 120)
                        ), cosBf),
                b1 = 5 + 3 * Tf + 10 * Cf - 4 * Cf2 - 9 * e2,
                b2 = 61 + 90 * Tf + 298 * Cf + 45 * Tf2 - 252 * e2 - 3 * Cf2,
                _b1_1 = b1 * D4,
                _b1 = Math_Double.div(_b1_1, 24),
                _b2_1 = b2 * D6,
                _b2 = Math_Double.div(_b2_1, 720),
                b = Bf - Math_Double.div(Nf * tanBf, Rf) * (0.5 * D2 - _b1 + _b2);
        return new double[]{Math.toDegrees(l) + l0, Math.toDegrees(b)};
    }
}
