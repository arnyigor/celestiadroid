package com.arny.celestiatools.data.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "planets")
class Planet {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long? = null
    var name: String? = null
    var radius: Double = 0.toDouble()//m
    var mass: Double = 0.toDouble()//kg
}