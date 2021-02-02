package net.aptivist.covidmappproject.data.api.interfaces

import androidx.lifecycle.LiveData
import net.aptivist.covidmappproject.data.api.models.CasesCountryList

interface ICasesCountriesService {
    fun getCasesCountries(): CasesCountryList?
}