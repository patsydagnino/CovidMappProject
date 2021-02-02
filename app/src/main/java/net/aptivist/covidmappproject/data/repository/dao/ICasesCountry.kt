package net.aptivist.covidmappproject.data.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import net.aptivist.covidmappproject.data.api.models.CasesCountry
import net.aptivist.covidmappproject.data.api.models.CasesCountryList

@Dao
interface ICasesCountry {
    @Query("SELECT * FROM CasesCountry")
    fun getAllCountries(): List<CasesCountry>

    @Insert
    fun insertCountries( countries: List<CasesCountry>)

    @Query("DELETE FROM CasesCountry")
    fun deleteAllCountries()
}