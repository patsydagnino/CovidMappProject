package net.aptivist.covidmappproject.ui.tips.view
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_tips.*
import kotlinx.android.synthetic.main.fragment_tips.view.*
import net.aptivist.covidmappproject.R
import net.aptivist.covidmappproject.ui.DrawerActivity
import net.aptivist.covidmappproject.ui.tips.adapter.TipsPagerAdapter
import net.aptivist.covidmappproject.ui.tips.viewmodel.TipsViewModel

class TipsFragment : Fragment() {

    private lateinit var tipsViewModel: TipsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        tipsViewModel= ViewModelProvider(this).get(TipsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tips, container, false)
        root.vpTips.adapter = TipsPagerAdapter()
        root.vpTips.registerOnPageChangeCallback(pageChangeCallback)
        Handler().postDelayed({addBottomDots(0)},200)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_next.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireActivity(), DrawerActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        })
    }

    private var pageChangeCallback: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            addBottomDots(position)
            if(position==4){
                btn_next.visibility=View.VISIBLE
            }
            else{
                btn_next.visibility=View.INVISIBLE
            }
        }
    }
    private fun addBottomDots(currentPage: Int) {
        val colorsActive = requireActivity().getColor(R.color.dot_light)
        val colorsInactive = requireActivity().getColor(R.color.dot_dark)
        layoutDots.removeAllViews();
        for (i in 0 until 5) {
            var dot = TextView(requireActivity())
            dot.text= Html.fromHtml(" &#8226;")
            dot.textSize=40f
            dot.setTextColor(colorsInactive)
            if(i==currentPage)
                dot.setTextColor(colorsActive)
            layoutDots.addView(dot)
        }
    }
}
