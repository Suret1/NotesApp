package com.suret.todoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.suret.todoapp.R
import com.suret.todoapp.data.model.ViewPagerModel
import com.suret.todoapp.data.viewmodel.NotesViewModel
import com.suret.todoapp.ui.fragments.all.AllFragment
import com.suret.todoapp.ui.fragments.folder.FolderFragment
import com.suret.todoapp.ui.main.adapters.MyViewPagerAdapter

class MainFragment : Fragment() {
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var pagerList: ViewPagerModel
    private lateinit var pagerAdapter: MyViewPagerAdapter
    private lateinit var contentView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::contentView.isInitialized) {
            contentView = inflater.inflate(R.layout.fragment_main, container, false)
        }
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listOfFragment: ArrayList<Fragment> = arrayListOf()
        val listOfTitle: ArrayList<String> = arrayListOf()

        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        listOfFragment.add(AllFragment())
        listOfFragment.add(FolderFragment())
        listOfTitle.add(resources.getString(R.string.all))
        listOfTitle.add(resources.getString(R.string.folder))


        pagerList = ViewPagerModel(listOfFragment, listOfTitle)

        pagerAdapter = MyViewPagerAdapter(pagerList, requireActivity().supportFragmentManager)

        viewPager.adapter = pagerAdapter

        tabLayout.setupWithViewPager(viewPager)

    }

}