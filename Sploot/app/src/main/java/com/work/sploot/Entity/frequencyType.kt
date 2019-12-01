package com.work.sploot.Entity

import android.os.Parcelable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "TblFrequencyType")
data class frequencyType(

    @ColumnInfo(name = "frequencyTypeId")
    @PrimaryKey(autoGenerate = true)
    var frequencyTypeId: Long? =null,
    @ColumnInfo(name = "frequencyTypeName") var frequencyTypeName: String? =null,
    @ColumnInfo(name = "active") var active: String? =null,
    @ColumnInfo(name = "syncStatus") var syncStatus: Int? =null
): Parcelable