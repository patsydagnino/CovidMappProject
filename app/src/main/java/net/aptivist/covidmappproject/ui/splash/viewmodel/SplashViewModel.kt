package net.aptivist.covidmappproject.ui.splash.viewmodel

import android.app.Activity
import android.content.Intent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.aptivist.covidmappproject.ui.base.BaseViewModel
import net.aptivist.covidmappproject.ui.DrawerActivity


class SplashViewModel: BaseViewModel() {

    fun hideSplash(activity: Activity){
       uiScope.launch {
            delay(4000)
            withContext(Dispatchers.Main){
                val intent = Intent(activity, DrawerActivity::class.java)
                activity.startActivity(intent)
                activity.finish()
            }
        }
    }

}