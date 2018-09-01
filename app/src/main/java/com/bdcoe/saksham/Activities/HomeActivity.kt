package com.bdcoe.saksham.Activities

import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import kotlinx.android.synthetic.main.activity_home.*
import com.elmargomez.typer.Typer
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import com.bdcoe.saksham.Adapters.BottomBarAdapter
import com.bdcoe.saksham.Dialogs.MedalTallyDialog
import com.bdcoe.saksham.Fragments.*
import com.bdcoe.saksham.R
import com.elmargomez.typer.Font


class HomeActivity : AppCompatActivity() {

    lateinit var bottomNavigationBar: AHBottomNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        toolbar_layout.title = resources.getString(R.string.saksham)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = resources.getColor(R.color.colorPrimary)
        } else window.statusBarColor = resources.getColor(R.color.colorAccent)

        window.clearFlags(FLAG_TRANSLUCENT_STATUS)
        window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.colorPrimary)
        window.navigationBarColor = resources.getColor(R.color.colorPrimary)

        toolbar_layout.setCollapsedTitleTextColor(resources.getColor(R.color.primary_text))
        toolbar_layout.setExpandedTitleColor(resources.getColor(R.color.primary_text))
        val font = Typer.set(this).getFont(Font.ROBOTO_LIGHT)
        toolbar_layout.setCollapsedTitleTypeface(font)
        toolbar_layout.setExpandedTitleTypeface(font)

        initializeNavigationBar()
        initializeViewPager()

    }

    private fun initializeViewPager() {
        view_pager.setPagingEnabled(false)
        val pagerAdapter = BottomBarAdapter(supportFragmentManager)

        pagerAdapter.addFragments(NewsFragment())
        pagerAdapter.addFragments(ScheduleFragment())
        pagerAdapter.addFragments(HomeFragment())
        pagerAdapter.addFragments(RegisterFragment())
        pagerAdapter.addFragments(AboutUsFragment())

        view_pager.adapter = pagerAdapter
        view_pager.currentItem = 2

    }

    private fun initializeNavigationBar() {
        val item0 = AHBottomNavigationItem(resources.getString(R.string.news), resources.getDrawable(R.drawable.neews))
        val item1 = AHBottomNavigationItem(resources.getString(R.string.schedule), resources.getDrawable(R.drawable.schedule))
        val item2 = AHBottomNavigationItem(resources.getString(R.string.home), resources.getDrawable(R.drawable.home))
        val item3 = AHBottomNavigationItem(resources.getString(R.string.register), resources.getDrawable(R.drawable.ic_person_add_black_24dp))
        val item4 = AHBottomNavigationItem(resources.getString(R.string.about_us), resources.getDrawable(R.drawable.ic_info_outline_black_48dp))

        bottomNavigationBar = bottom_navigation
        bottomNavigationBar.addItem(item0)
        bottomNavigationBar.addItem(item1)
        bottomNavigationBar.addItem(item2)
        bottomNavigationBar.addItem(item3)
        bottomNavigationBar.addItem(item4)

        bottomNavigationBar.defaultBackgroundColor = resources.getColor(R.color.colorPrimary)
        bottomNavigationBar.accentColor = resources.getColor(R.color.colorAccent)
        bottomNavigationBar.inactiveColor = resources.getColor(R.color.secondary_text)
        bottomNavigationBar.isBehaviorTranslationEnabled = true
        bottomNavigationBar.currentItem = 2

        bottomNavigationBar.setOnTabSelectedListener { position, wasSelected ->

            if (!wasSelected)
                view_pager.currentItem = position
            when (position){
                0 -> toolbar_layout.title = resources.getString(R.string.news)
                1 -> toolbar_layout.title = resources.getString(R.string.schedule)
                2 -> toolbar_layout.title = resources.getString(R.string.saksham)
                3 -> toolbar_layout.title = resources.getString(R.string.register)
                4 -> toolbar_layout.title = resources.getString(R.string.about_us)
            }
            return@setOnTabSelectedListener true
        }


    }
}
