package net.aptivist.covidmappproject.data.api.implementations

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.aptivist.covidmappproject.helpers.COUNTRIESENDPOINT
import net.aptivist.covidmappproject.data.api.interfaces.ICasesCountriesService
import net.aptivist.covidmappproject.data.api.models.CasesCountry
import net.aptivist.covidmappproject.data.api.models.CasesCountryList
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject

class CasesCountriesServiceImpl(private val client: OkHttpClient, private val gson: Gson, private val baseURL: String) : ICasesCountriesService {

    override fun getCasesCountries(): CasesCountryList? {

        val request = Request.Builder().url(baseURL + COUNTRIESENDPOINT).get().build()

        val call = runBlocking(Dispatchers.IO) {
            client.newCall(request).execute()
        }

        val response = call.body?.string()

        var casesCountry : CasesCountryList? = null

        if (response != null){

            try {

                val listOfCases = mutableListOf<CasesCountry>()

                val jsonObject = JSONObject(response).getJSONArray("countryitems").getJSONObject(0)

                jsonObject.keys().forEach { keyStr ->
                    if (keyStr != "stat") {
                        val currentObj = JSONObject(jsonObject.get(keyStr).toString())
                        listOfCases.add(
                            gson.fromJson(
                                currentObj.toString(),
                                CasesCountry::class.java
                            )
                        )
                    }
                }

                casesCountry = CasesCountryList(listOfCases)

            } catch (e: JSONException){
                //malformed json
                e.printStackTrace()
            } catch (ex: Exception){
                //another exception
                ex.printStackTrace()
            }


        }

        return casesCountry
    }

}