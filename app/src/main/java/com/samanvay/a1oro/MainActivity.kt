package com.samanvay.a1oro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.samanvay.a1oro.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        replaceFragment(home())
        binding.bottomNavigation.background = null
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){

                R.id.BottomBar_Home-> replaceFragment(home())
                R.id.BottomBar_Explore->replaceFragment(ExploreListings())
                R.id.BottomBar_Help->replaceFragment(help())
                R.id.BottomBar_Your_Bookings->replaceFragment(yourBookings())
            }
            true
        }

    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in,
            R.anim.fade_out,
            R.anim.fade_out,
            R.anim.slide_in)
//            .setReorderingAllowed(true)
            .replace(R.id.Frame_layout,fragment).commit()
    }
}






