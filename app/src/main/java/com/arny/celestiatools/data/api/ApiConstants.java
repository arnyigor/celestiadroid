package com.arny.celestiatools.data.api;

public class ApiConstants {
	/**
	 * Orbits for all asteroids in the MPC database
	 */
	public static final String ALL = "http://minorplanetcenter.net/Extended_Files/mpcorb_extended.json.gz";
	/**
	 * Orbits for Near Earth Asteroids (NEAs)
	 */
	public static final String NEA = "http://minorplanetcenter.net/Extended_Files/nea_extended.json.gz";
	/**
	 * Orbits for Potentially Hazardous Asteroids (PHAs
	 */
	public static final String PHA = "http://minorplanetcenter.net/Extended_Files/pha_extended.json.gz";
	/**
	 * Orbits from the latest DOU MPEC
	 */
	public static final String DAILY = "http://minorplanetcenter.net/Extended_Files/daily_extended.json.gz";
	/**
	 * Orbits for TNOs, Centaurs and SDOs
	 */
	public static final String DISTANT = "http://minorplanetcenter.net/Extended_Files/distant_extended.json.gz";
	/**
	 * Orbits for asteroids with e ≥ 0.5 or q > 6 AU
	 */
	public static final String UNUSUAL = "http://minorplanetcenter.net/Extended_Files/unusual_extended.json.gz";
	/**
	 * 	Orbits for current comets in the MPC database
	 */
	public static final String COMETS = "http://www.minorplanetcenter.net/Extended_Files/cometels.json.gz";
	/**
	 * NASA api_key
	 */
	public static final String NASA_API_KEY = "JgXsJaEdEaL91BcQWyEMVWAjPQkOId7nWDOgMQfz";
}
