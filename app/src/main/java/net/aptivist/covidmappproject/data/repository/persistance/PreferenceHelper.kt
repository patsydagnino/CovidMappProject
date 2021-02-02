package net.aptivist.covidmappproject.data.repository.persistance

import android.content.Context
import android.content.SharedPreferences
import net.aptivist.covidmappproject.helpers.SHARED_PREF

class PreferenceHelper {
    private lateinit var appPref : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    fun setString(key:String, value: String?){
        editor = appPref.edit()
        editor.putString(key,value)
        editor.apply()
    }

    fun getString(key : String) : String? = appPref.getString( key,null)
    fun clearString(key : String) = setString(key, null)
    companion object{
        fun newInstance(context: Context): PreferenceHelper?{
            val pHelper =
                PreferenceHelper()
            pHelper.appPref = context.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)
            return pHelper
        }

    }
}