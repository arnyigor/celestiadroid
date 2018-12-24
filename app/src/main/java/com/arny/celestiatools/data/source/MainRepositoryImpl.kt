package com.arny.celestiatools.data.source

import android.content.Context
import com.arny.celestiatools.CelestiaApp
import com.arny.celestiatools.data.db.AppDB
import com.arny.celestiatools.data.db.PlanetDao

class MainRepositoryImpl : BaseRepository, RemoteRepository, LocalRepository, PlanetRepository {
    private object Holder {
        val INSTANCE = MainRepositoryImpl()
    }

    companion object {
        val instance: MainRepositoryImpl by lazy { Holder.INSTANCE }
    }

    override fun getContext(): Context {
        return CelestiaApp.appContext
    }

    override fun getPlanetDao(): PlanetDao {
        return AppDB.getInstance(getContext()).planetDao
    }
}
