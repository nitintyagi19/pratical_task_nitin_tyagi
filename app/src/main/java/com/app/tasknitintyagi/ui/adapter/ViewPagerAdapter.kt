package com.app.tasknitintyagi.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.tasknitintyagi.ui.fragment.FeedsFragment
import com.app.tasknitintyagi.ui.fragment.ProfileFragment



class ViewPagerAdapter(var list: ArrayList<String>, var context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FeedsFragment()
            1 -> ProfileFragment()
            else -> FeedsFragment()
        }
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position]
    }
}
