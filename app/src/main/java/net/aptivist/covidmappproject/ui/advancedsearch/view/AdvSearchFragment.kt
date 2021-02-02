package net.aptivist.covidmappproject.ui.advancedsearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.nav_header_drawer.*
import net.aptivist.covidmappproject.R
import net.aptivist.covidmappproject.ui.advancedsearch.viewmodel.AdvSearchViewModel

class AdvSearchFragment : Fragment() {

    private lateinit var advSearchViewModel: AdvSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        advSearchViewModel= ViewModelProvider(this).get(AdvSearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_advsearch, container, false)
        advSearchViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
