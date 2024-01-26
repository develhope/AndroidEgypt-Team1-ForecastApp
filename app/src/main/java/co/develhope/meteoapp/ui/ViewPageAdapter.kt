//package co.develhope.meteoapp.ui
//
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentActivity
//import androidx.viewpager2.adapter.FragmentStateAdapter
//import co.develhope.meteoapp.ui.home.HomeScreenFragment
//import co.develhope.meteoapp.ui.search.SearchScreenFragment
//import co.develhope.meteoapp.ui.today.TodayScreenFragment
//import co.develhope.meteoapp.ui.tomorrow.TomorrowScreenFragment
//
//class ViewPageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
//
//    override fun getItemCount(): Int {
//        return 4
//    }
//
//    override fun createFragment(position: Int): Fragment {
//        return when(position){
//            0 -> HomeScreenFragment()
//            1 -> TodayScreenFragment()
//            2 -> TomorrowScreenFragment()
//            3 -> SearchScreenFragment()
//            else -> throw IllegalArgumentException("Invalid position: $position")
//        }
//    }
//}