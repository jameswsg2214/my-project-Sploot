package com.work.sploot.Entity

import android.os.Parcelable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.ClientInfoStatus
import java.sql.Time
import java.util.*

@Parcelize
@Entity(tableName = "TblTaskRemainder")
data class taskRemainder(

    @ColumnInfo(name = "taskRemainderId")
    @PrimaryKey(autoGenerate = true)
    var taskRemainderId: Long? =null,
    @ColumnInfo(name = "taskCategoryId") var taskCategoryId:Int?,
    @ColumnInfo(name = "medicationId") var medicationId:Int?=null,
    @ColumnInfo(name = "taskDate") var taskDate: Date?,
    @ColumnInfo(name = "taskTime") var taskTime: Time?,
    @ColumnInfo(name="taskStatus") var taksStatus: String?,
    @ColumnInfo(name="snooze") var snooze: String?,
    @ColumnInfo(name = "active") var active: String? =null,
    @ColumnInfo(name = "createdDate") var createdDate: Date?,
    @ColumnInfo(name = "updatedDate") var updatedDate: Date? =null,
    @ColumnInfo(name = "syncStatus") var syncStatus: Int? =null
): Parcelable