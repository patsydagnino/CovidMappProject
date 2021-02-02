package net.aptivist.covidmappproject.ui.map.view

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import net.aptivist.covidmappproject.R
import net.aptivist.covidmappproject.data.api.models.*
import net.aptivist.covidmappproject.data.repository.AppDataBase
import net.aptivist.covidmappproject.data.repository.dao.ICasesCountry
import net.aptivist.covidmappproject.data.repository.dao.ICasesGlobalStatus
import net.aptivist.covidmappproject.data.repository.dao.ICasesUSAStateDao
import net.aptivist.covidmappproject.helpers.DATABASE_LOG_TAG
import net.aptivist.covidmappproject.helpers.DATABASE_NAME
import net.aptivist.covidmappproject.helpers.USPostalAbbreviationStates
import net.aptivist.covidmappproject.ui.map.viewmodel.MapViewModel
import org.koin.android.ext.android.inject
import java.time.LocalTime

class MapActivity : AppCompatActivity() {
    private val mapViewModel : MapViewModel by inject()
    private val gson = Gson()
    private val dbCasesUSAStates : ICasesUSAStateDao by inject()
    private val dbCasesGlobal : ICasesGlobalStatus by inject()
    private val dbCasesCountry : ICasesCountry by inject()
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        var state = ""
        mapViewModel.getCasesUSAStates()
        mapViewModel.casesUSAStates.observe(this, Observer {
            val casesStatesList = it
            casesStatesList?.forEach {
                when (it.state) {
                    "AL"-> it.state = USPostalAbbreviationStates.AL.abbreviation
                    "AK" -> it.state = USPostalAbbreviationStates.AK.abbreviation
                    "AS" -> it.state = USPostalAbbreviationStates.AS.abbreviation
                    "AZ"-> it.state = USPostalAbbreviationStates.AZ.abbreviation
                    "AR"-> it.state = USPostalAbbreviationStates.AR.abbreviation
                    "CA"-> it.state = USPostalAbbreviationStates.CA.abbreviation
                    "CO"-> it.state = USPostalAbbreviationStates.CO.abbreviation
                    "CT"-> it.state = USPostalAbbreviationStates.CT.abbreviation
                    "DE"-> it.state = USPostalAbbreviationStates.DE.abbreviation
                    "FL"-> it.state = USPostalAbbreviationStates.FL.abbreviation
                    "GA"-> it.state = USPostalAbbreviationStates.GA.abbreviation
                    "GU"-> it.state = USPostalAbbreviationStates.GU.abbreviation
                    "HI"-> it.state = USPostalAbbreviationStates.HI.abbreviation
                    "ID"-> it.state = USPostalAbbreviationStates.ID.abbreviation
                    "IL"-> it.state = USPostalAbbreviationStates.IL.abbreviation
                    "IN"-> it.state = USPostalAbbreviationStates.IN.abbreviation
                    "IA"-> it.state = USPostalAbbreviationStates.IA.abbreviation
                    "KS"-> it.state = USPostalAbbreviationStates.KS.abbreviation
                    "KY"-> it.state = USPostalAbbreviationStates.KY.abbreviation
                    "LA"-> it.state = USPostalAbbreviationStates.LA.abbreviation
                    "ME"-> it.state = USPostalAbbreviationStates.ME.abbreviation
                    "MD"-> it.state = USPostalAbbreviationStates.MD.abbreviation
                    "MA"-> it.state = USPostalAbbreviationStates.MA.abbreviation
                    "MI"-> it.state = USPostalAbbreviationStates.MI.abbreviation
                    "MN"-> it.state = USPostalAbbreviationStates.MN.abbreviation
                    "MS"-> it.state = USPostalAbbreviationStates.MS.abbreviation
                    "MO"-> it.state = USPostalAbbreviationStates.MO.abbreviation
                    "MT"-> it.state = USPostalAbbreviationStates.MT.abbreviation
                    "NE"-> it.state = USPostalAbbreviationStates.NE.abbreviation
                    "NV"-> it.state = USPostalAbbreviationStates.NV.abbreviation
                    "NH"-> it.state = USPostalAbbreviationStates.NH.abbreviation
                    "NJ"-> it.state = USPostalAbbreviationStates.NJ.abbreviation
                    "NM"-> it.state = USPostalAbbreviationStates.NM.abbreviation
                    "NY"-> it.state = USPostalAbbreviationStates.NY.abbreviation
                    "NC"-> it.state = USPostalAbbreviationStates.NC.abbreviation
                    "ND"-> it.state = USPostalAbbreviationStates.ND.abbreviation
                    "OH"-> it.state = USPostalAbbreviationStates.OH.abbreviation
                    "OK"-> it.state = USPostalAbbreviationStates.OK.abbreviation
                    "OR"-> it.state = USPostalAbbreviationStates.OR.abbreviation
                    "PA"-> it.state = USPostalAbbreviationStates.PA.abbreviation
                    "PR"-> it.state = USPostalAbbreviationStates.PR.abbreviation
                    "RI"-> it.state = USPostalAbbreviationStates.RI.abbreviation
                    "SC"-> it.state = USPostalAbbreviationStates.SC.abbreviation
                    "SD"-> it.state = USPostalAbbreviationStates.SD.abbreviation
                    "TN"-> it.state = USPostalAbbreviationStates.TN.abbreviation
                    "TX"-> it.state = USPostalAbbreviationStates.TX.abbreviation
                    "UT"-> it.state = USPostalAbbreviationStates.UT.abbreviation
                    "VT"-> it.state = USPostalAbbreviationStates.VT.abbreviation
                    "VA"-> it.state = USPostalAbbreviationStates.VA.abbreviation
                    "VI"-> it.state = USPostalAbbreviationStates.VI.abbreviation
                    "WA"-> it.state = USPostalAbbreviationStates.WA.abbreviation
                    "WV"-> it.state = USPostalAbbreviationStates.WV.abbreviation
                    "WI"-> it.state = USPostalAbbreviationStates.WI.abbreviation
                    "WY"-> it.state = USPostalAbbreviationStates.WY.abbreviation
                }
            state += "State: ${it.state}\n Positive: ${it.positive} \n Negative: ${it.negative} \n" +
                    "Recovered: ${it.recovered} \n Total: ${it.totalTestResults} \n Date: ${it.dateChecked}\n"
            }
            Log.d( LocalTime.now().toString() + " Results USA States ", state)
            if (casesStatesList != null) {
                runBlocking { saveCasesUSAStates(casesStatesList) }
            }
        })
        mapViewModel.getGlobalStatus()
        var global = ""
        mapViewModel.casesGlobalStatus.observe(this, Observer{
            val globalStatus = it
            globalStatus?.results?.forEach{
                global += "Total Cases: ${it.total_cases}\n Total Recovered: ${it.total_recovered}\n" +
                        "Total Unresolved: ${it.total_unresolved} \n Total Deaths: ${it.total_deaths} \n"
            }
            Log.d("Results Global Status ",global )
            if (globalStatus != null) {
                val gsList:List<CasesGlobalStatus> = globalStatus.results!!.map { it }
                runBlocking { saveCasesGlobalStatus(gsList) }
            }
        })

        mapViewModel.getCasesCountries()
        var  countries = ""
        mapViewModel.casesCountries.observe(this, Observer {
            var casesCountries = it
            casesCountries?.countryitems?.forEach{
                countries += "Country: ${it.title} \nTotal Cases: ${it.total_cases}\n Total Recovered: ${it.total_recovered}\n" +
                        "Total Unresolved: ${it.total_unresolved} \n Total Deaths: ${it.total_deaths} \n"
            }
            Log.d("Results Countries ",countries )
            if (casesCountries != null) {
                val casesCountriesList:List<CasesCountry> = casesCountries.countryitems.map { it }
                runBlocking { saveCasesCountry(casesCountriesList) }
            }
        })
    }
    private suspend fun saveCasesUSAStates(states:List<CasesUSAState>){
        withContext(Dispatchers.IO){
            dbCasesUSAStates.deleteAllStates()
            Log.i(DATABASE_LOG_TAG,"Deleted all data from CasesUSAStates table")
            dbCasesUSAStates.insertStates(states)
            Log.i(DATABASE_LOG_TAG,"Inserted Data")
            var casesList = dbCasesUSAStates.getAllStates()
            Log.i(DATABASE_LOG_TAG,"Retrieved Data: ${gson.toJson(casesList)}")
        }
    }
    private suspend fun saveCasesGlobalStatus(global: List<CasesGlobalStatus>) {
        withContext(Dispatchers.IO) {
            dbCasesGlobal.deleteAllGlobalStatuses()
            Log.i(DATABASE_LOG_TAG, "Deleted all data from CasesGlobalStatus table")
            dbCasesGlobal.insertGlobalStatus(global)
            Log.i(DATABASE_LOG_TAG, "Inserted Data")
            var casesList = dbCasesGlobal.getAllGlobalStatuses()
            Log.i(DATABASE_LOG_TAG, "Retrieved Data: ${gson.toJson(casesList)}")
        }
    }
    private suspend fun saveCasesCountry( countries:List<CasesCountry>) {
        withContext(Dispatchers.IO) {
            dbCasesCountry.deleteAllCountries()
            Log.i(DATABASE_LOG_TAG, "Deleted all data from CasesGlobalStatus table")
            dbCasesCountry.insertCountries(countries)
            Log.i(DATABASE_LOG_TAG, "Inserted Data")
            var casesList = dbCasesCountry.getAllCountries()
            Log.i(DATABASE_LOG_TAG, "Retrieved Data: ${gson.toJson(casesList)}")
        }
    }
    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}
