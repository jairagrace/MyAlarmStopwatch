package jaira.cabarrubias.com.alarmstopwatch.user.alarmstopwatchapplication

import android.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var pagerAdapter:AdapterFragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)

        pagerAdapter = AdapterFragment(supportFragmentManager)

        pagerAdapter!!.addFragments(AlarmFragment(), "Alarm")
        pagerAdapter!!.addFragments(StopwatchFragment(), "StopWatch")


        costomViewpager.adapter = pagerAdapter
        costomTabLayout.setupWithViewPager(costomViewpager)


    }
}