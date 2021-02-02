package net.aptivist.covidmappproject.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import net.aptivist.covidmappproject.data.api.models.CasesCountry
import net.aptivist.covidmappproject.data.api.models.CasesGlobalStatus
import net.aptivist.covidmappproject.data.api.models.CasesUSAState
import net.aptivist.covidmappproject.data.repository.dao.ICasesCountry
import net.aptivist.covidmappproject.data.repository.dao.ICasesGlobalStatus
import net.aptivist.covidmappproject.data.repository.dao.ICasesUSAStateDao
import net.aptivist.covidmappproject.helpers.DATABASE_VERSION

@Database(entities = [CasesUSAState::class, CasesCountry::class, CasesGlobalStatus::class], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun casesUSAStateDao():ICasesUSAStateDao
    abstract fun casesGlobalStatusDao():ICasesGlobalStatus
    abstract fun casesCountryDao():ICasesCountry
}