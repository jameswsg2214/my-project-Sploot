package com.work.sploot.Entity

import android.os.Parcelable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Date

@Parcelize
@Entity(tableName = "TblBrandMaster")
data class brandMaster(

    @ColumnInfo(name = "brandId")
    @PrimaryKey(autoGenerate = true)
    var brandId: Long? =null,
    @ColumnInfo(name = "brandName") var brandName: Int? =null,
    @ColumnInfo(name = "brndDate") var brndDate: Date? =null,
    @ColumnInfo(name = "active") var active: String? =null,
    @ColumnInfo(name = "syncStatus") var syncStatus: Int? =null
): Parcelable