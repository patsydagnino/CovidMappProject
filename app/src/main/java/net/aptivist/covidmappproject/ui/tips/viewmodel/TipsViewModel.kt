package net.aptivist.covidmappproject.ui.tips.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TipsViewModel : ViewModel() {
    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result
    fun initViewHolder(){

    }
}