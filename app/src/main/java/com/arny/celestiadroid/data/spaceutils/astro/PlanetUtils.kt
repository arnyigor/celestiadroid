package com.arny.celestiadroid.data.spaceutils.astro

import com.arny.celestiadroid.data.utils.parseDouble


/**
 * return radius with exponent
 */
fun getPlanetRadius(parseMass: Double, expVal: Int?): Double {
    var planetMass = parseMass
    if (expVal != null) {
        val massExp = "${parseMass}E$expVal"
        planetMass = massExp.parseDouble() ?: parseMass
    }
    return planetMass
}


fun getExpVal(x: Double): String {
    return x.toString().split("E".toRegex()).getOrNull(1) ?: ""
}

fun getNotExpVal(x: Double): String {
    return x.toString().split("E".toRegex()).getOrNull(0) ?: ""
}