package com.arny.celestiatools.utils.astro;



public class OrbitCalc {


  public static CircularMotion calcCircularOrbit(double mass, double radius, double Hp){
      return new CircularMotion(mass, radius, Hp);
    }

    public static EllipseMotion calcEllipseOrbit(double Mass, double Radius, double Hp, double Ha, double Vp, double ecc, double sma){
        return new EllipseMotion(Mass, Radius, Hp, Ha, Vp,ecc,sma);
    }


}
