package com.arny.celestiatools.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Asteroid {
    /**
     * r.m.s. residual (")
     */
    @SerializedName("rms")
    @Expose
    var rms: Double? = null
    /**
     * Semimajor axis, a (AU)
     */
    @SerializedName("a")
    @Expose
    var a: Double? = null
    /**
     * Number of observations
     */
    @SerializedName("Num_obs")
    @Expose
    var numObs: Int? = null
    /**
     * Inclination to the ecliptic, J2000.0 (degrees)
     */
    @SerializedName("i")
    @Expose
    var i: Double? = null
    /**
     * Absolute magnitude, H
     */
    @SerializedName("H")
    @Expose
    var h: Double? = null
    /**
     * Synodic period (years)
     */
    @SerializedName("Synodic_period")
    @Expose
    var synodicPeriod: Double? = null
    /**
     * Possible values:Atira,Aten,Apollo,Amor,Object with perihelion distance < 1.665 AU,Hungaria,MBA,Phocaea,Hilda,Jupiter Trojan,Distant Object,Unclassified
     */
    @SerializedName("Orbit_type")
    @Expose
    var orbitType: String? = null
    /**
     * Longitude of the ascending node, ☊, J2000.0 (degrees)
     */
    @SerializedName("Node")
    @Expose
    var node: Double? = null
    /**
     * 4-hexdigit flags (refer to entry in Table 1 for explanation; in JSON format this information has been decoded and is supplied in individual keywords)
     */
    @SerializedName("Hex_flags")
    @Expose
    var hexFlags: String? = null
    /**
     * Other provisional designations (if they exist)
     */
    @SerializedName("Other_desigs")
    @Expose
    var otherDesigs: List<String>? = null
    @SerializedName("Tp")
    @Expose
    var tp: Double? = null
    /**
     * Precise indicator of perturbers used in orbit computation
     */
    @SerializedName("Perturbers_2")
    @Expose
    var perturbers2: String? = null
    /**
     * Reference
     */
    @SerializedName("Ref")
    @Expose
    var ref: String? = null
    /**
     * Orbital eccentricity, e
     */
    @SerializedName("e")
    @Expose
    var e: Double? = null
    /**
     * Value = 1 if flag raised, otherwise keyword is absent
     */
    @SerializedName("One_km_NEO_flag")
    @Expose
    var oneKmNEOFlag: Int? = null
    /**
     * Perihelion distance (AU)
     */
    @SerializedName("Perihelion_dist")
    @Expose
    var perihelionDist: Double? = null
    /**
     * Semilatus rectum distance (AU)
     */
    @SerializedName("Semilatus_rectum")
    @Expose
    var semilatusRectum: Double? = null
    /**
     * Number of oppositions
     */
    @SerializedName("Num_opps")
    @Expose
    var numOpps: Int? = null
    /**
     * Epoch of the orbit (Julian Date)
     */
    @SerializedName("Epoch")
    @Expose
    var epoch: Double? = null
    /**
     * Value = 1 if flag raised, otherwise keyword is absent
     */
    @SerializedName("NEO_flag")
    @Expose
    private var nEOFlag: Int? = null
    /**
     * Orbital period (years)
     */
    @SerializedName("Orbital_period")
    @Expose
    var orbitalPeriod: Double? = null
    /**
     * Number, if the asteroid has received one; this is the asteroid's permanent designation
     */
    @SerializedName("Number")
    @Expose
    var number: String? = null
    /**
     * Only present for multi-opposition orbits (year of first observation – year of last observation)
     */
    @SerializedName("Arc_years")
    @Expose
    var arcYears: String? = null
    /**
     * Aphelion distance (AU)
     */
    @SerializedName("Aphelion_dist")
    @Expose
    var aphelionDist: Double? = null
    /**
     * Argument of perihelion, ω, J2000.0 (degrees)
     */
    @SerializedName("Peri")
    @Expose
    var peri: Double? = null
    /**
     * Principal provisional designation (if it exists)
     */
    @SerializedName("Principal_desig")
    @Expose
    var principalDesig: String? = null
    /**
     * Date of last observation included in orbit solution (YYYY-MM-DD format)
     */
    @SerializedName("Last_obs")
    @Expose
    var lastObs: String? = null
    /**
     * Mean daily motion, n (degrees/day)
     */
    @SerializedName("n")
    @Expose
    var n: Double? = null
    /**
     * Name, if the asteroid has received one
     */
    @SerializedName("Name")
    @Expose
    var name: String? = null
    /**
     * Slope parameter, G
     */
    @SerializedName("G")
    @Expose
    var g: Double? = null
    /**
     * Uncertainty parameter, U (integer with values 0–9; but refer to entry in Table 1 for other possible values)
     */
    @SerializedName("U")
    @Expose
    var u: String? = null
    /**
     * Mean anomaly, M, at the epoch (degrees)
     */
    @SerializedName("M")
    @Expose
    var m: Double? = null
    /**
     * Name of orbit computer (be it a person or machine)
     */
    @SerializedName("Computer")
    @Expose
    var computer: String? = null
    /**
     * Coarse indicator of perturbers used in orbit computation
     */
    @SerializedName("Perturbers")
    @Expose
    var perturbers: String? = null

    fun getnEOFlag(): Int? {
        return nEOFlag
    }

    fun setnEOFlag(nEOFlag: Int?) {
        this.nEOFlag = nEOFlag
    }
}
