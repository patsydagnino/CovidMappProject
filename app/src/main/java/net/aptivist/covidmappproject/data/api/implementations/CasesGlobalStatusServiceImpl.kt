package net.aptivist.covidmappproject.data.api.implementations

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.aptivist.covidmappproject.data.api.interfaces.ICasesGlobalStatusService
import net.aptivist.covidmappproject.data.api.interfaces.retrofit.IRCasesGlobalStatusService
import net.aptivist.covidmappproject.data.api.models.CasesCountryList
import net.aptivist.covidmappproject.data.api.models.CasesGlobalStatusList
import retrofit2.Call
import retrofit2.Retrofit

class CasesGlobalStatusServiceImpl(private val retrofit: Retrofit): ICasesGlobalStatusService {
    override fun getGlobalStatus(): CasesGlobalStatusList? {
        val call = retrofit.create(IRCasesGlobalStatusService::class.java)
        return runBlocking (Dispatchers.IO){
            call.getGlobalStatus().body()
        }
    }
}