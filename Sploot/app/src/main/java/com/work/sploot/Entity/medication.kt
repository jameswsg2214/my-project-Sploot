package com.work.sploot.Entity

import android.os.Parcelable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Date

@Parcelize
@Entity(tableName = "TblMedication")
data class medication(

    @ColumnInfo(name = "medicationId")
    @PrimaryKey(autoGenerate = true)
    var medicationId: Long? =null,
    @ColumnInfo(name = "userId") var userId: Int?,
    @ColumnInfo(name = "petId") var petId: Int?,
    @ColumnInfo(name = "medicineTypeId") var medicineTypeId: Int?,  //MedicineType : Tablet,Capsue,Drop,Injection,etc..
    @ColumnInfo(name = "medicineName") var medicineName: String?,
    @ColumnInfo(name = "medicineCompany") var medicineCompany: String?,
    @ColumnInfo(name = "intakeAdvice") var intakeAdvice: String?, //None,BeforeMeal,Withmeal,AfterMEal,Custome Input
    @ColumnInfo(name = "partsOfTheDay") var partsOfTheDay: String?, //After Noon,Eve,MidNight,Mooday,Dawn,Dusk
    @ColumnInfo(name = "medicineStartDate") var medicineStartDate: Date? =null,
    @ColumnInfo(name = "medicineEndtDate") var medicineEndtDate: Date? =null,
    @ColumnInfo(name = "frequency") var frequency: String, //JSON object frequecy and frequency settings
    @ColumnInfo(name = "remainder") var remainder: String, //JSON object for remainders and remainderSettings
    @ColumnInfo(name = "active") var active: String? =null,
    @ColumnInfo(name = "createdDate") var createdDate: Date?,
    @ColumnInfo(name = "updatedDate") var updatedDate: Date? =null,
    @ColumnInfo(name = "syncStatus") var syncStatus: Int? =null

): Parcelable