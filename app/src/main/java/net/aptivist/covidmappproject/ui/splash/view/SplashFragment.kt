package net.aptivist.covidmappproject.ui.splash.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_splash.*

import net.aptivist.covidmappproject.R
import net.aptivist.covidmappproject.ui.map.viewmodel.MapViewModel
import net.aptivist.covidmappproject.ui.splash.viewmodel.SplashViewModel

class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    private lateinit var splasViewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        splasViewModel= ViewModelProvider(this).get(SplashViewModel::class.java)
        animImgWelcome.setImageAssetsFolder("raw/")
        animImgWelcome.setAnimation(R.raw.coronavirus)
        animImgWelcome.playAnimation()
        // TODO: Use the ViewModel
    }

}
