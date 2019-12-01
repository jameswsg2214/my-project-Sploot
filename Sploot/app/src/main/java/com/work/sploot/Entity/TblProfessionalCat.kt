package com.work.sploot.Entity

import android.os.Parcelable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "TblProfessionalCat")
data class dayupdate(
    @ColumnInfo(name = "updateCatId")
    @PrimaryKey(autoGenerate = true)
    var updateCatId: Long? =null,
    @ColumnInfo(name = "profCatName") var profCatName: String? =null,
    @ColumnInfo(name = "active") var active: String? =null,
    @ColumnInfo(name = "syncStatus") var syncStatus: Int? =null
): Parcelable