package net.aptivist.covidmappproject.data.api.interfaces.retrofit

import net.aptivist.covidmappproject.data.api.models.CasesUSAState
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface IRCasesUSAStatesService {
    @GET("api/v1/states/current.json")
    suspend fun getCasesUSAStates(): Response<List<CasesUSAState>?>
}