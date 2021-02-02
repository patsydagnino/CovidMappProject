package net.aptivist.covidmappproject.data.api.interfaces

import net.aptivist.covidmappproject.data.api.models.CasesCountryList
import net.aptivist.covidmappproject.data.api.models.CasesGlobalStatusList

interface ICasesGlobalStatusService {
    fun getGlobalStatus() : CasesGlobalStatusList?
}