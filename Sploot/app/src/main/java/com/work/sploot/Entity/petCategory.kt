package com.work.sploot.Entity

import android.os.Parcelable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "TblPetCategory")
data class petCategory(

    @ColumnInfo(name = "PetCategoryId")
    @PrimaryKey(autoGenerate = true)
    var petCategoryId: Long? =null,
    @ColumnInfo(name = "categoryName") var categoryName: String? =null,
    @ColumnInfo(name = "active") var active: String? =null,
    @ColumnInfo(name = "syncStatus") var syncStatus: Int? =null
): Parcelable