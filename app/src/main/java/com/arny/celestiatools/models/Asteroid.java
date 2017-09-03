package com.arny.celestiatools.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Asteroid {
	/**
	 * r.m.s. residual (")
	 */
	@SerializedName("rms")
	@Expose
	private Double rms;
	/**
	 * Semimajor axis, a (AU)
	 */
	@SerializedName("a")
	@Expose
	private Double a;
	/**
	 * Number of observations
	 */
	@SerializedName("Num_obs")
	@Expose
	private Integer numObs;
	/**
	 * Inclination to the ecliptic, J2000.0 (degrees)
	 */
	@SerializedName("i")
	@Expose
	private Double i;
	/**
	 * Absolute magnitude, H
	 */
	@SerializedName("H")
	@Expose
	private Double h;
	/**
	 * Synodic period (years)
	 */
	@SerializedName("Synodic_period")
	@Expose
	private Double synodicPeriod;
	/**
	 * Possible values:Atira,Aten,Apollo,Amor,Object with perihelion distance < 1.665 AU,Hungaria,MBA,Phocaea,Hilda,Jupiter Trojan,Distant Object,Unclassified
	 */
	@SerializedName("Orbit_type")
	@Expose
	private String orbitType;
	/**
	 * Longitude of the ascending node, ☊, J2000.0 (degrees)
	 */
	@SerializedName("Node")
	@Expose
	private Double node;
	/**
	 * 4-hexdigit flags (refer to entry in Table 1 for explanation; in JSON format this information has been decoded and is supplied in individual keywords)
	 */
	@SerializedName("Hex_flags")
	@Expose
	private String hexFlags;
	/**
	 * Other provisional designations (if they exist)
	 */
	@SerializedName("Other_desigs")
	@Expose
	private List<String> otherDesigs = null;
	@SerializedName("Tp")
	@Expose
	private Double tp;
	/**
	 * Precise indicator of perturbers used in orbit computation
	 */
	@SerializedName("Perturbers_2")
	@Expose
	private String perturbers2;
	/**
	 * Reference
	 */
	@SerializedName("Ref")
	@Expose
	private String ref;
	/**
	 * Orbital eccentricity, e
	 */
	@SerializedName("e")
	@Expose
	private Double e;
	/**
	 *  Value = 1 if flag raised, otherwise keyword is absent
	 */
	@SerializedName("One_km_NEO_flag")
	@Expose
	private Integer oneKmNEOFlag;
	/**
	 * Perihelion distance (AU)
	 */
	@SerializedName("Perihelion_dist")
	@Expose
	private Double perihelionDist;
	/**
	 * Semilatus rectum distance (AU)
	 */
	@SerializedName("Semilatus_rectum")
	@Expose
	private Double semilatusRectum;
	/**
	 * Number of oppositions
	 */
	@SerializedName("Num_opps")
	@Expose
	private Integer numOpps;
	/**
	 * Epoch of the orbit (Julian Date)
	 */
	@SerializedName("Epoch")
	@Expose
	private Double epoch;
	/**
	 * Value = 1 if flag raised, otherwise keyword is absent
	 */
	@SerializedName("NEO_flag")
	@Expose
	private Integer nEOFlag;
	/**
	 * Orbital period (years)
	 */
	@SerializedName("Orbital_period")
	@Expose
	private Double orbitalPeriod;
	/**
	 * Number, if the asteroid has received one; this is the asteroid's permanent designation
	 */
	@SerializedName("Number")
	@Expose
	private String number;
	/**
	 * Only present for multi-opposition orbits (year of first observation – year of last observation)
	 */
	@SerializedName("Arc_years")
	@Expose
	private String arcYears;
	/**
	 * Aphelion distance (AU)
	 */
	@SerializedName("Aphelion_dist")
	@Expose
	private Double aphelionDist;
	/**
	 * Argument of perihelion, ω, J2000.0 (degrees)
	 */
	@SerializedName("Peri")
	@Expose
	private Double peri;
	/**
	 * Principal provisional designation (if it exists)
	 */
	@SerializedName("Principal_desig")
	@Expose
	private String principalDesig;
	/**
	 * Date of last observation included in orbit solution (YYYY-MM-DD format)
	 */
	@SerializedName("Last_obs")
	@Expose
	private String lastObs;
	/**
	 * Mean daily motion, n (degrees/day)
	 */
	@SerializedName("n")
	@Expose
	private Double n;
	/**
	 *  Name, if the asteroid has received one
	 */
	@SerializedName("Name")
	@Expose
	private String name;
	/**
	 * Slope parameter, G
	 */
	@SerializedName("G")
	@Expose
	private Double g;
	/**
	 *  Uncertainty parameter, U (integer with values 0–9; but refer to entry in Table 1 for other possible values)
	 */
	@SerializedName("U")
	@Expose
	private String u;
	/**
	 * Mean anomaly, M, at the epoch (degrees)
	 */
	@SerializedName("M")
	@Expose
	private Double m;
	/**
	 * Name of orbit computer (be it a person or machine)
	 */
	@SerializedName("Computer")
	@Expose
	private String computer;
	/**
	 * Coarse indicator of perturbers used in orbit computation
	 */
	@SerializedName("Perturbers")
	@Expose
	private String perturbers;

	public Double getRms() {
		return rms;
	}

	public void setRms(Double rms) {
		this.rms = rms;
	}

	public Double getA() {
		return a;
	}

	public void setA(Double a) {
		this.a = a;
	}

	public Integer getNumObs() {
		return numObs;
	}

	public void setNumObs(Integer numObs) {
		this.numObs = numObs;
	}

	public Double getI() {
		return i;
	}

	public void setI(Double i) {
		this.i = i;
	}

	public Double getH() {
		return h;
	}

	public void setH(Double h) {
		this.h = h;
	}

	public Double getSynodicPeriod() {
		return synodicPeriod;
	}

	public void setSynodicPeriod(Double synodicPeriod) {
		this.synodicPeriod = synodicPeriod;
	}

	public String getOrbitType() {
		return orbitType;
	}

	public void setOrbitType(String orbitType) {
		this.orbitType = orbitType;
	}

	public Double getNode() {
		return node;
	}

	public void setNode(Double node) {
		this.node = node;
	}

	public String getHexFlags() {
		return hexFlags;
	}

	public void setHexFlags(String hexFlags) {
		this.hexFlags = hexFlags;
	}

	public List<String> getOtherDesigs() {
		return otherDesigs;
	}

	public void setOtherDesigs(List<String> otherDesigs) {
		this.otherDesigs = otherDesigs;
	}

	public Double getTp() {
		return tp;
	}

	public void setTp(Double tp) {
		this.tp = tp;
	}

	public String getPerturbers2() {
		return perturbers2;
	}

	public void setPerturbers2(String perturbers2) {
		this.perturbers2 = perturbers2;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Double getE() {
		return e;
	}

	public void setE(Double e) {
		this.e = e;
	}

	public Integer getOneKmNEOFlag() {
		return oneKmNEOFlag;
	}

	public void setOneKmNEOFlag(Integer oneKmNEOFlag) {
		this.oneKmNEOFlag = oneKmNEOFlag;
	}

	public Double getPerihelionDist() {
		return perihelionDist;
	}

	public void setPerihelionDist(Double perihelionDist) {
		this.perihelionDist = perihelionDist;
	}

	public Double getSemilatusRectum() {
		return semilatusRectum;
	}

	public void setSemilatusRectum(Double semilatusRectum) {
		this.semilatusRectum = semilatusRectum;
	}

	public Integer getNumOpps() {
		return numOpps;
	}

	public void setNumOpps(Integer numOpps) {
		this.numOpps = numOpps;
	}

	public Double getEpoch() {
		return epoch;
	}

	public void setEpoch(Double epoch) {
		this.epoch = epoch;
	}

	public Integer getnEOFlag() {
		return nEOFlag;
	}

	public void setnEOFlag(Integer nEOFlag) {
		this.nEOFlag = nEOFlag;
	}

	public Double getOrbitalPeriod() {
		return orbitalPeriod;
	}

	public void setOrbitalPeriod(Double orbitalPeriod) {
		this.orbitalPeriod = orbitalPeriod;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getArcYears() {
		return arcYears;
	}

	public void setArcYears(String arcYears) {
		this.arcYears = arcYears;
	}

	public Double getAphelionDist() {
		return aphelionDist;
	}

	public void setAphelionDist(Double aphelionDist) {
		this.aphelionDist = aphelionDist;
	}

	public Double getPeri() {
		return peri;
	}

	public void setPeri(Double peri) {
		this.peri = peri;
	}

	public String getPrincipalDesig() {
		return principalDesig;
	}

	public void setPrincipalDesig(String principalDesig) {
		this.principalDesig = principalDesig;
	}

	public String getLastObs() {
		return lastObs;
	}

	public void setLastObs(String lastObs) {
		this.lastObs = lastObs;
	}

	public Double getN() {
		return n;
	}

	public void setN(Double n) {
		this.n = n;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getG() {
		return g;
	}

	public void setG(Double g) {
		this.g = g;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public Double getM() {
		return m;
	}

	public void setM(Double m) {
		this.m = m;
	}

	public String getComputer() {
		return computer;
	}

	public void setComputer(String computer) {
		this.computer = computer;
	}

	public String getPerturbers() {
		return perturbers;
	}

	public void setPerturbers(String perturbers) {
		this.perturbers = perturbers;
	}
}
