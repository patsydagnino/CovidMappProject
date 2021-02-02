package net.aptivist.covidmappproject.data.api.interfaces

import androidx.lifecycle.LiveData
import net.aptivist.covidmappproject.data.api.models.CasesCountryList
import net.aptivist.covidmappproject.data.api.models.CasesMexicoStateList

interface ICasesMexicoStatesService {
    fun getCases(): CasesMexicoStateList?
}