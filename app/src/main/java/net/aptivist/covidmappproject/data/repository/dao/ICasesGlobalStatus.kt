package net.aptivist.covidmappproject.data.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import net.aptivist.covidmappproject.data.api.models.CasesGlobalStatus
import net.aptivist.covidmappproject.data.api.models.CasesGlobalStatusList

@Dao
interface ICasesGlobalStatus {
    @Query("SELECT * FROM CasesGlobalStatus")
    fun getAllGlobalStatuses(): List<CasesGlobalStatus>

    @Insert
    fun insertGlobalStatus(global: List<CasesGlobalStatus>)

    @Query("DELETE FROM CasesGlobalStatus")
    fun deleteAllGlobalStatuses()
}