package net.aptivist.covidmappproject.data.api.interfaces.retrofit

import net.aptivist.covidmappproject.data.api.models.CasesGlobalStatusList
import retrofit2.Response
import retrofit2.http.GET

interface IRCasesGlobalStatusService {
    @GET("free-api?global=stats")
    suspend fun getGlobalStatus():Response<CasesGlobalStatusList?>

}