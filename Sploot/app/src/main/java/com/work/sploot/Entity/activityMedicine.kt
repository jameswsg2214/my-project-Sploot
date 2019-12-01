package com.work.sploot.Entity

import android.os.Parcelable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Date

@Parcelize
@Entity(tableName = "TblActivityMedicine")
data class activityMedicine(

    @ColumnInfo(name = "activityMedicineId")
    @PrimaryKey(autoGenerate = true)
    var activityMedicineId: Long? =null,
    @ColumnInfo(name = "userId") var userId: Int?,
    @ColumnInfo(name = "petId") var petId: Int?,
    @ColumnInfo(name = "medicineTypeId") var medicineTypeId: Int?,
    @ColumnInfo(name = "medicineName") var medicineName: String?,
    @ColumnInfo(name = "medicineDate") var noteDate: Date? =null,
    @ColumnInfo(name = "active") var active: String? =null,
    @ColumnInfo(name = "createdDate") var createdDate: Date?,
    @ColumnInfo(name = "updatedDate") var updatedDate: Date? =null,
    @ColumnInfo(name = "syncStatus") var syncStatus: Int? =null
): Parcelable