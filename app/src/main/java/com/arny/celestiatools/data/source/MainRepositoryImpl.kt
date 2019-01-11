package com.arny.celestiatools.data.source

import android.content.Context
import com.arny.celestiatools.CelestiaApp
import com.arny.celestiatools.data.db.AppDB
import com.arny.celestiatools.data.db.PlanetDao
import io.realm.Realm

class MainRepositoryImpl : BaseRepository, RemoteRepository, LocalRepository, PlanetRepository {
    private lateinit var realm: Realm
    private object Holder {
        val INSTANCE = MainRepositoryImpl()
    }

    override fun getDB(): Realm {
        realm = Realm.getDefaultInstance()
        return realm
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
