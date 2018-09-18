package com.opop.brazius.nbapredictor


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

internal class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val title = arrayOf("Eastern conference", "Western Conference", "Finals")

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return EastFragment.getInstance(position)
            1 -> return WestFragment.getInstance(position)
            2 -> return FinalsFragment.getInstance(position)
        }
        return EastFragment.getInstance(position)
    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}