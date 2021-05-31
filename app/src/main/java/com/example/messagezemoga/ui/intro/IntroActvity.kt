package com.example.messagezemoga.ui.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.viewpager2.widget.ViewPager2
import com.example.messagezemoga.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.intro_activity.*

class IntroActvity : AppCompatActivity() {

    //region Properties
    private lateinit var viewPageAdapter: ViewPageAdapter
    private lateinit var listaIntroItem: List<ScreenItem>
    private lateinit var animation:Animation
    //endregion


    //region OverrideMethods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.intro_activity)
        supportActionBar?.hide()
        animation = AnimationUtils.loadAnimation(applicationContext, R.anim.button_animation)
        initViewPager()
        initlistenerTab()
        initClickButton()

    }

    //endregion

    private fun initClickButton() {
        button_next.setOnClickListener {
            var position = view_pager_intro.currentItem
            val sizeList = listaIntroItem.size
            if (position < sizeList) {
                position++
                view_pager_intro.setCurrentItem(position, true)
            }
            if (position == sizeList - 1) {
                loadLastScreen()
            }
        }
        buttonStarted.setOnClickListener{
            finish()
        }
    }

    private fun initlistenerTab() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == listaIntroItem.size - 1)
                    loadLastScreen()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun loadLastScreen() {
        buttonStarted.visibility = View.VISIBLE
        button_next.visibility = View.GONE
        tabLayout.visibility = View.GONE
        buttonStarted.animation = animation
    }

    private fun initViewPager() {
        viewPageAdapter = ViewPageAdapter(getListIntro(), this)
        view_pager_intro.adapter = viewPageAdapter
        view_pager_intro.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(tabLayout, view_pager_intro) { _, _ -> }.attach()
    }

    private fun getListIntro(): List<ScreenItem> {
        val listaIntro = mutableListOf<ScreenItem>()
        listaIntro.add(ScreenItem(baseContext.getString(R.string.tile_explication_screen_1), baseContext.getString(R.string.decription_screen_1), R.drawable.ic_see_post))
        listaIntro.add(ScreenItem(baseContext.getString(R.string.tile_explication_screen_2), baseContext.getString(R.string.decription_screen_2), R.drawable.ic_commentic))
        listaIntro.add(ScreenItem(baseContext.getString(R.string.tile_explication_screen_3), baseContext.getString(R.string.decription_screen_3), R.drawable.ic_delete_info_app))
        listaIntroItem = listaIntro
        return listaIntro
    }


}