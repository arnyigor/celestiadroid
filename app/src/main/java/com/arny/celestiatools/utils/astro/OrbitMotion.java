package com.arny.celestiatools.utils.astro;



public class OrbitMotion {

  public static CircularMotion calcCircularOrbit(double mass, double radius, double Hp){
      return new CircularMotion(mass, radius, Hp).calc();
//      double Uskorenie = circularMotion.getUskorenie();
//      int hour = circularMotion.getHour();
//      int min = circularMotion.getMin();
//      int sec = circularMotion.getSec();
//      double V1 = circularMotion.getV1();
//      double V2 = circularMotion.getV2();
    }

    public static EllipseMotion calcEllipseOrbit(double Mass,double Radius,double Hp,double Vp){
        return new EllipseMotion(Mass, Radius, Hp, Vp).calc();
//        double Uskorenie = ellipseMotion.getUskorenie();
//        int hour = ellipseMotion.getHour();
//        int min = ellipseMotion.getMin();
//        int sec = ellipseMotion.getSec();
//        double V1 = ellipseMotion.getV1();
//        double V2 = ellipseMotion.getV2();
//        double Va = ellipseMotion.getVa();
//        double Ecc = ellipseMotion.getEcc();
//        double Ha = ellipseMotion.getHa();
//        double SMA = ellipseMotion.getSma();
    }


}
