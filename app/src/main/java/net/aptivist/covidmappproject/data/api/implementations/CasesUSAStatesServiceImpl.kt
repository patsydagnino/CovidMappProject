package net.aptivist.covidmappproject.data.api.implementations

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.aptivist.covidmappproject.data.api.interfaces.ICaseUSAStatesService
import net.aptivist.covidmappproject.data.api.interfaces.retrofit.IRCasesUSAStatesService
import net.aptivist.covidmappproject.data.api.models.CasesUSAState
import retrofit2.Retrofit

class CasesUSAStatesServiceImpl(private val retrofit: Retrofit) : ICaseUSAStatesService {
    override fun getCases(): List<CasesUSAState>? {
        val call = retrofit.create(IRCasesUSAStatesService::class.java)
        return runBlocking(Dispatchers.IO) {
            call.getCasesUSAStates().body()
        }
    }
}