package com.arny.celestiatools.data.models

import com.arny.celestiatools.data.utils.Utility
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

//@Entity(tableName = "planets")
open class Planet : RealmObject() {
    //    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "_id")
    @PrimaryKey
    var id: Long? = null
    var name: String? = null
    var radius: Double = 0.toDouble()//m
    var mass: Double = 0.toDouble()//kg

    override fun toString(): String {
        return Utility.getFields(this)
    }
}