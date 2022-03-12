package com.app.tasknitintyagi.ui


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.tasknitintyagi.R
import com.app.tasknitintyagi.databinding.ActivityMainBinding
import com.app.tasknitintyagi.ui.adapter.ViewPagerAdapter
import com.app.tasknitintyagi.util.AppDialog
import com.app.tasknitintyagi.util.Resource
import com.app.tasknitintyagi.util.Utility
import com.app.tasknitintyagi.util.getViewModelFactory
import com.app.tasknitintyagi.viewModel.HomeViewModel
import com.google.android.material.tabs.TabLayout


class HomeActivity : BaseActivity<ActivityMainBinding, HomeViewModel>() {

    override val layoutId: Int
        get() = R.layout.activity_main
    override val viewModel: HomeViewModel by viewModels { getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callFeedList()
    }

    private fun callFeedList() {

        mViewModel.feedListCall()
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            mViewModel.showProgress.value = false
                            resource.data?.let {
                               Utility.CityList = it
                                setupViewPager()
                            }
                        }
                        Resource.Status.ERROR -> {
                            AppDialog(this@HomeActivity, "Feed List failed", true)
                            mViewModel.showProgress.value = false
                        }
                    }
                }
            })
    }

    private fun setupViewPager() {
        var title = ArrayList<String>()
        title.add("Feeds")
        title.add("Profile")

        val viewPager = mBinding.mainViewPager
        viewPager.adapter = ViewPagerAdapter(title, applicationContext, supportFragmentManager)
        val tabs: TabLayout = mBinding.tabLayout
        tabs.setupWithViewPager(viewPager)
    }
}