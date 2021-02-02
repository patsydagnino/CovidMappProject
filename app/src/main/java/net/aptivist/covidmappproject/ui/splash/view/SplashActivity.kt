package net.aptivist.covidmappproject.ui.splash.view
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.aptivist.covidmappproject.R
import net.aptivist.covidmappproject.databinding.ActivitySplashBinding
import net.aptivist.covidmappproject.ui.DrawerActivity
import net.aptivist.covidmappproject.ui.map.viewmodel.MapViewModel
import net.aptivist.covidmappproject.ui.splash.viewmodel.SplashViewModel
import net.aptivist.covidmappproject.ui.tips.view.TipsFragment

class SplashActivity : AppCompatActivity() {
    private lateinit var splashViewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
        binding.lifecycleOwner=this
        splashViewModel= ViewModelProvider(this).get(SplashViewModel::class.java)

        binding.splashViewModel=splashViewModel

        //splashViewModel.hideSplash(this@SplashActivity)

        val splashFragment = SplashFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, splashFragment).commit()
        //super.setTheme( R.style.MyAppTheme );
        hideSplash()
    }

    private fun hideSplash(){
        splashViewModel.uiScope.launch {
            delay(3000)
            withContext(Dispatchers.Main){
                /*val intent = Intent(this@SplashActivity,DrawerActivity::class.java)
                startActivity(intent)
                finish()*/


                val tipsFragment = TipsFragment()
                val args = Bundle()
                //args.putInt(TipsFragment.ARG_POSITION, position)
                //newFragment.arguments = args

                val transaction = supportFragmentManager.beginTransaction().apply {
                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    replace(R.id.fragment_container, tipsFragment)
                    addToBackStack(null)
                }

                // Commit the transaction
                transaction.commit();

            }
        }
    }
}
