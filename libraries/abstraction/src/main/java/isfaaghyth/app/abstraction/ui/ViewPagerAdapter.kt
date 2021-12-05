package isfaaghyth.app.abstraction.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    private val mFragmentList = arrayListOf<Fragment>()

    override fun createFragment(position: Int): Fragment = mFragmentList[position]

    override fun getItemCount(): Int = mFragmentList.size

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

}