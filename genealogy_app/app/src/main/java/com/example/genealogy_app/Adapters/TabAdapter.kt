package com.example.genealogy_app.Adapters

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.genealogy_app.Fragments.HomeFragment
import com.example.genealogy_app.Fragments.ProfileFragment
import com.example.genealogy_app.Fragments.TreeListFragment

/*class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int  = 3

    override fun getItem(i: Int): Fragment {
        var fragment: Fragment

        when(i) {
            0 -> fragment = HomeFragment()
            1 -> fragment = TreeListFragment()
            2 -> fragment = ProfileFragment()
            else -> fragment = HomeFragment()
        }

        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        when(position) {
            0 -> return "Home"
            1 -> return "Trees"
            2 -> return "Profile"
            else -> return "error"
        }
    }
}*/