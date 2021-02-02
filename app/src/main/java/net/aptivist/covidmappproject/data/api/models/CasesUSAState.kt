package net.aptivist.covidmappproject.data.api.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CasesUSAState(@ColumnInfo(name = "state") var state : String,
                         @ColumnInfo(name = "positive")var positive: Int?,
                         @ColumnInfo(name = "negative") var negative : Int?,
                         @ColumnInfo(name = "recovered") var recovered: Int?,
                         @ColumnInfo(name = "death") var death: Int?,
                         @ColumnInfo(name = "totalTestResults") var totalTestResults : Int?,
                         @ColumnInfo(name = "dateChecked") var dateChecked: String?,
                         @ColumnInfo(name = "stateString") var stateString: String?,
                         @PrimaryKey(autoGenerate = true) val id: Int =0)