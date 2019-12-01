package com.work.sploot.Entity

import android.os.Parcelable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "TblProfessionals")
data class professionals(

    @ColumnInfo(name = "professionalsId")
    @PrimaryKey(autoGenerate = true)
    var professionalsId: Long? =null,
    @ColumnInfo(name = "professionalsName") var professionalsName: String? =null,
    @ColumnInfo(name = "profCatId") var profCatId: Int? =null,
    @ColumnInfo(name = "active") var active: String? =null,
    @ColumnInfo(name = "syncStatus") var syncStatus: Int? =null
): Parcelable