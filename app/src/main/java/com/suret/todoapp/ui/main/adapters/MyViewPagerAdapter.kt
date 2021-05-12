package com.suret.todoapp.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.suret.todoapp.data.model.ViewPagerModel

class MyViewPagerAdapter(private val list: ViewPagerModel, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return list.listFragment.size
    }

    override fun getItem(position: Int): Fragment {
        return list.listFragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return list.title[position]
    }

}