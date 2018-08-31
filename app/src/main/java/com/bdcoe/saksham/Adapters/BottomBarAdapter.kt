package com.bdcoe.saksham.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import java.nio.file.Files.size



/**
 * Created by rishi on 31/8/18.
 */
class BottomBarAdapter(fragmentManager: FragmentManager) : SmartFragmentStatePagerAdapter(fragmentManager) {
    private val fragments = ArrayList<Fragment>()
    // Our custom method that populates this Adapter with Fragments
    fun addFragments(fragment: Fragment) {
        fragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}