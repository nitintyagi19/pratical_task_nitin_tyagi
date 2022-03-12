package com.app.tasknitintyagi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.app.tasknitintyagi.R
import com.app.tasknitintyagi.databinding.FeedsFragmentLayoutBinding
import com.app.tasknitintyagi.ui.adapter.ViewPagerSubAdapter
import com.app.tasknitintyagi.util.Utility.TabCurrentPosition
import com.google.android.material.tabs.TabLayout

class FeedsFragment: Fragment() {

    lateinit var binding:FeedsFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.feeds_fragment_layout, container, false)
        setupViewPager()
        return binding.root
    }

    private fun setupViewPager() {
        var title = ArrayList<String>()
        title.add("ALL")
        title.add("Chicago")
        title.add("NewYork")
        title.add("Los Angeles")
        val viewPager = binding.viewPagerOne
        viewPager.adapter = ViewPagerSubAdapter(title,this.requireContext() ,this.childFragmentManager)
        val tabs: TabLayout = binding.tabLayout
        tabs.setupWithViewPager(viewPager)
        viewPager.currentItem = tabs.selectedTabPosition
        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                tabs.setScrollPosition(position,0f,true);
                viewPager.currentItem = position;
                TabCurrentPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

}