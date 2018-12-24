package com.arny.celestiatools.data.source

import com.arny.celestiatools.data.db.PlanetDao
import com.arny.celestiatools.data.models.Planet


interface PlanetRepository : BaseRepository {
    fun getPlanetDao(): PlanetDao

    fun getAllPlanets(): ArrayList<Planet> {
        return ArrayList(getPlanetDao().queryPlanets())
    }

    fun getPlanet(id: Long?): Planet? {
        return getPlanetDao().queryPlanet(id)
    }

    fun insertPlanet(planet: Planet): Long {
        return getPlanetDao().insert(planet)
    }

    fun updatePlanet(planet: Planet): Int {
        return getPlanetDao().update(planet)
    }
}