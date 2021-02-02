package net.aptivist.covidmappproject.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.content_drawer.*
import net.aptivist.covidmappproject.R
import net.aptivist.covidmappproject.data.repository.persistance.PreferenceHelper
import net.aptivist.covidmappproject.helpers.PREF_DARKMODE

class DrawerActivity() : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var pHelper : PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
        pHelper = PreferenceHelper.newInstance(applicationContext)!!
        initView()
        //val toolbar: Toolbar = findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar)
        //supportActionBar?.hide()

        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_map, R.id.nav_advsearch, R.id.nav_tips
            ), drawerLayout
        )
       // setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }
    private fun initView(){
        btnDrawer.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.openDrawer(GravityCompat.START)
                else drawerLayout.closeDrawer(
                    GravityCompat.END
                )
            }
        })

       val darkmode_switch:Switch=navView.getMenu().findItem(R.id.darkmode).getActionView().findViewById(R.id.darkmode_switch)

        darkmode_switch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                saveDarkMode("1")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                saveDarkMode("0")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    private fun saveDarkMode(value:String){
        pHelper.setString(PREF_DARKMODE,value)
        //Log.d("Dark mode: ", "$PREF_DARKMODE")
        //Toast.makeText(this, "Dark mode: ${pHelper.getString(PREF_DARKMODE) ?:""}", Toast.LENGTH_SHORT).show()
    }




}

