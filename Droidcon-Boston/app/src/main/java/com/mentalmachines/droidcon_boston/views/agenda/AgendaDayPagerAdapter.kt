package com.mentalmachines.droidcon_boston.views.agenda

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.mentalmachines.droidcon_boston.data.Schedule
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup


class AgendaDayPagerAdapter internal constructor(fm: FragmentManager, private val myAgenda: Boolean)
    : FixedFragmentStatePagerAdapter(fm) {
    private var registeredFragments = SparseArray<Fragment>()
    private val PAGE_COUNT = 2
    private val tabTitles = arrayOf("Day 1", "Day 2")

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment {
        return AgendaDayFragment.newInstance(myAgenda,
                if (position == 0) Schedule.MONDAY else Schedule.TUESDAY
        )
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        registeredFragments.put(position, fragment)
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    override fun updateFragmentItem(position: Int, fragment: Fragment) {
        if (fragment is AgendaDayFragment) {
            fragment.updateList()
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, fragmentObj: Any) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, fragmentObj)
    }

    override fun getFragmentItem(position: Int): Fragment {
        return getItem(position)
    }

    fun getRegisteredFragment(position: Int): Fragment {
        return registeredFragments.get(position)
    }

}