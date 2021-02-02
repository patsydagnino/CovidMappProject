package net.aptivist.covidmappproject.ui.tips.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_tips.view.*
import net.aptivist.covidmappproject.R


class TipsPagerAdapter: RecyclerView.Adapter<TipsPagerAdapter.ViewHolder>() {
    //private val tipsViewModel:TipsViewModel by viewModel()


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
             LayoutInflater.from(parent.context).inflate(R.layout.item_tips, parent, false)
        )

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.run {
            var title = ""
            var description = ""
            when (position) {
                0 -> {
                    title = context.getString(R.string.tip_title1)
                    description = context.getString(R.string.tip_description1)
                    if(AppCompatDelegate.getDefaultNightMode()!= AppCompatDelegate.MODE_NIGHT_YES){
                        animImgTips.setAnimation(R.raw.stayhome)
                    }else{
                        animImgTips.setAnimation(R.raw.stayhome_night)
                    }

                }
                1 -> {
                    title = context.getString(R.string.tip_title2)
                    description = context.getString(R.string.tip_description2)
                    animImgTips.setAnimation(R.raw.distancing)
                }
                2 -> {
                    title = context.getString(R.string.tip_title3)
                    description = context.getString(R.string.tip_description3)
                    animImgTips.setAnimation(R.raw.wash)
                }
                3 -> {
                    title = context.getString(R.string.tip_title4)
                    description = context.getString(R.string.tip_description4)
                    animImgTips.setAnimation(R.raw.handsanitizer)
                }
                4 -> {
                    title = context.getString(R.string.tip_title5)
                    description = context.getString(R.string.tip_description5)
                    animImgTips.setAnimation(R.raw.careprovider)
                }
            }

            tvTipsTitle.text = title
            tvTipsMessage.text = description
        }
    }
}

/*private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return TipsPlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}*/