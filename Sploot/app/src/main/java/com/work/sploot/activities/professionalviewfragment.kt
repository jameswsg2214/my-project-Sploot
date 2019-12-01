package com.work.sploot.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.work.sploot.R

class professionalviewfragment:Fragment() {

    companion object {
        fun newInstance(): professionalviewfragment {
            return professionalviewfragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val views= inflater.inflate(R.layout.professioanl_fragment, container, false)
        val tabLayout = views.findViewById<TabLayout>(R.id.tab_pro)
        var viewPager = views.findViewById<ViewPager>(R.id.pro_view_pager)
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Doctor"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Groomer"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Breeder"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Pet Shop"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val mContext = activity
        val fm = mContext?.supportFragmentManager
        val transaction = fm?.beginTransaction()
        transaction?.replace(R.id.pro_view_pager, docter_pro.newInstance("Doctor"))
        transaction?.commit()

        var adapter = MyAdapters_process(views.context, fm!!, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        return views
    }

class MyAdapters_process(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> {
                return docter_pro()
            }
            1 -> {
                return Goomer_pro()
            }
            2 -> {
                return Breeder_pro()
            }
            3 -> {
                return Petshop_pro()
            }

            else -> return null
        }
    }
}
}
