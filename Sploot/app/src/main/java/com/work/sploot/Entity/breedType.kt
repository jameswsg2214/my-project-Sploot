package com.work.sploot.Entity

import android.os.Parcelable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "TblBreedType")
data class breedType(

    @ColumnInfo(name = "breedTypeId")
    @PrimaryKey(autoGenerate = true)
    var breedTypeId: Long? =null,
    @ColumnInfo(name = "breedTypeName") var breedTypeName: String? =null,
    @ColumnInfo(name = "active") var active: String? =null,
    @ColumnInfo(name = "syncStatus") var syncStatus: Int? =null
): Parcelable