package net.aptivist.covidmappproject.ui.map.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.aptivist.covidmappproject.data.api.interfaces.ICaseUSAStatesService
import net.aptivist.covidmappproject.data.api.interfaces.ICasesCountriesService
import net.aptivist.covidmappproject.data.api.interfaces.ICasesGlobalStatusService
import net.aptivist.covidmappproject.data.api.interfaces.ICasesMexicoStatesService
import net.aptivist.covidmappproject.data.api.models.CasesCountryList
import net.aptivist.covidmappproject.data.api.models.CasesMexicoStateList
import net.aptivist.covidmappproject.data.api.models.CasesGlobalStatusList
import net.aptivist.covidmappproject.ui.base.BaseViewModel
import net.aptivist.covidmappproject.data.api.models.CasesUSAState

import net.aptivist.covidmappproject.data.repository.CasesUSAStateRepository
class MapViewModel(
    private val casesUSAStatesService: ICaseUSAStatesService,
    private val casesGlobalStatusService: ICasesGlobalStatusService,
    private val casesCountriesService: ICasesCountriesService,
    private val casesMexicoStatesService: ICasesMexicoStatesService):BaseViewModel() {

    private val _casesMexicoStates = MutableLiveData<CasesMexicoStateList?>()
    private val _casesUSAStates = MutableLiveData<List<CasesUSAState>?>()
    private val _casesGlobalStatus = MutableLiveData<CasesGlobalStatusList?>()
    private val _casesCountries = MutableLiveData<CasesCountryList?>()
    val casesMexicoStates: LiveData<CasesMexicoStateList?> = _casesMexicoStates
    val casesUSAStates: LiveData<List<CasesUSAState>?> = _casesUSAStates
    val casesGlobalStatus: LiveData<CasesGlobalStatusList?> = _casesGlobalStatus
    val casesCountries: LiveData<CasesCountryList?> = _casesCountries

    private var _casesNumber=MutableLiveData<String>("133.974")
    private var _place=MutableLiveData<String>("México")
    private var _date=MutableLiveData<String>("Mon 4th Junio 12:09 AM")

    val casesNumber: LiveData<String> = _casesNumber
    val place: LiveData<String> = _place
    val date: LiveData<String> = _date

    fun getTotalConfirmedCases(){
        _casesNumber.value="133.974"
        _place.value="México"
        _date.value="Mon 4th Junio 12:09 AM"

    }
    fun getTotalRecoveredCases(){
        _casesNumber.value="97.198"
        _place.value="México"
        _date.value="Mon 4th Junio 12:09 AM"
    }
    fun getTotalDeath(){
        _casesNumber.value="15.944"
        _place.value="México"
        _date.value="Mon 4th Junio 12:09 AM"

    }

    fun getCasesUSAStates() {
        _casesUSAStates.value = casesUSAStatesService.getCases()
    }

    fun getGlobalStatus() {
        _casesGlobalStatus.value = casesGlobalStatusService.getGlobalStatus()
    }

    fun getCasesCountries() {
        _casesCountries.value = casesCountriesService.getCasesCountries()
    }

    /*fun getCasesUSAStatesDB(){
        _casesUSAStates.value = casesUSAStateRepository.data
    }*/



    fun getCasesMexicoStates(){
        _casesMexicoStates.value=casesMexicoStatesService.getCases()
    }

}