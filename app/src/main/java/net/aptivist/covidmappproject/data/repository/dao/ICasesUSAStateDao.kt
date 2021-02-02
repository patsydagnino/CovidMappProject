package net.aptivist.covidmappproject.data.repository.dao

import androidx.room.*
import net.aptivist.covidmappproject.data.api.models.CasesUSAState

@Dao
interface ICasesUSAStateDao {

    @Query("SELECT * FROM CasesUSAState")
    fun getAllStates(): List<CasesUSAState>

    @Insert
    fun insertStates(states: List<CasesUSAState>)

    @Query("DELETE FROM CasesUSAState")
    fun deleteAllStates()
}