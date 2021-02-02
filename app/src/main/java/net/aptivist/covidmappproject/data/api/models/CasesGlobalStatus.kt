package net.aptivist.covidmappproject.data.api.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CasesGlobalStatus (@ColumnInfo(name = "total_cases") var total_cases: String,
                              @ColumnInfo(name = "total_recovered") var total_recovered: String,
                              @ColumnInfo(name = "total_unresolved") var total_unresolved: String,
                              @ColumnInfo(name = "total_deaths") var total_deaths: String,
                              @ColumnInfo(name = "total_new_cases_today") var total_new_cases_today: String,
                              @ColumnInfo(name = "total_new_deaths_today") var total_new_deaths_today: String,
                              @ColumnInfo(name = "total_active_cases") var total_active_cases: String,
                              @ColumnInfo(name = "total_serious_cases") var total_serious_cases:String,
                              @ColumnInfo(name = "total_affected_countries") var total_affected_countries: String,
                              @PrimaryKey(autoGenerate = true) val id: Int =0)