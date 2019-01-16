package com.arny.celestiadroid.data.source

import com.arny.celestiadroid.data.db.PlanetDao
import com.arny.celestiadroid.data.models.Planet
import io.realm.ImportFlag
import io.realm.Realm
import io.realm.internal.IOException


interface PlanetRepository : BaseRepository {
    fun getDB(): Realm
    fun getPlanetDao(): PlanetDao

    fun addPlanet(planet: Planet): Long? {
        val realm = getDB()
        realm.beginTransaction()
        var id: Long? = null
        try {
            if (planet.id == null) {
                val currentIdNum = realm.where(Planet::class.java).max("id")
                val index = if (currentIdNum == null) 0 else currentIdNum.toLong() + 1
                planet.id = index
            }
            val copyToRealm = realm.copyToRealmOrUpdate(planet, ImportFlag.CHECK_SAME_VALUES_BEFORE_SET)
            id = copyToRealm.id
            realm.commitTransaction()
        } catch (e: IOException) {
            realm.cancelTransaction()
        } finally {
            realm.close()
        }
        return id
    }

    fun loadPlanet(id: Long): Planet? {
        val realm = getDB()
        var planet: Planet? = null
        realm.beginTransaction()
        try {
           val realmObject = realm.where(Planet::class.java).equalTo("id", id).findFirst()
            if (realmObject != null) {
                planet = realm.copyFromRealm(realmObject)
            }
            realm.commitTransaction()
        } catch (e: IOException) {
            realm.cancelTransaction()
        } finally {
            realm.close()
        }
        return planet
    }

    fun loadPlanets(): ArrayList<Planet> {
        val list = arrayListOf<Planet>()
        val realm = getDB()
        realm.beginTransaction()
        try {
            val realmResults = realm.where(Planet::class.java).findAll()
            for (realmResult in realmResults) {
                list.add(realm.copyFromRealm(realmResult))
            }
            realm.commitTransaction()
        } catch (e: IOException) {
            realm.cancelTransaction()
        } finally {
            realm.close()
        }
        return list
    }

    fun removePlanet(id: Long) {
        val realm = getDB()
        realm.beginTransaction()
        try {
            val planet = realm.where(Planet::class.java).equalTo("id", id).findFirst()
            planet?.deleteFromRealm()
            realm.commitTransaction()
        } catch (e: IOException) {
            realm.cancelTransaction()
        } finally {
            realm.close()
        }
    }

}