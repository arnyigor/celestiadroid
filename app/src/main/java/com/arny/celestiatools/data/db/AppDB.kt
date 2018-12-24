package com.arny.celestiatools.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.arny.celestiatools.data.models.Planet
import com.arny.sentry.data.utils.SingletonHolder

@Database(entities = [Planet::class], exportSchema = false, version = 1)
abstract class AppDB : RoomDatabase() {

    companion object : SingletonHolder<AppDB, Context>({
        val context = it.applicationContext
        val databaseBuilder = Room.databaseBuilder(context, AppDB::class.java, "celestiadroid.db")
        databaseBuilder.build()
    })

    abstract val planetDao: PlanetDao
}
