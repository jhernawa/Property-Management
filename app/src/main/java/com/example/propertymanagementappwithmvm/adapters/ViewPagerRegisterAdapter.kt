package com.example.propertymanagementappwithmvm.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.propertymanagementappwithmvm.fragments.LandlordFragment
import com.example.propertymanagementappwithmvm.fragments.TenantFragment

class ViewPagerRegisterAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> LandlordFragment()
            else -> TenantFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Landlord"
            else -> "Tenant"
        }
    }

}