package com.work.sploot.Entity

import android.os.Parcelable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
@Parcelize
@Entity(tableName = "State")
data class State(

    @ColumnInfo(name = "State")
    @PrimaryKey(autoGenerate = true)
    var Stateid:  Long? =null,
    @ColumnInfo(name = "State") var Statenname: String? =null,
    @ColumnInfo(name = "active") var active: String? =null,
    @ColumnInfo(name = "syncStatus") var syncStatus: Int? =null
): Parcelable
