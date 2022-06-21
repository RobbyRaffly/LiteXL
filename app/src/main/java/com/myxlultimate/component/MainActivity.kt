package com.myxlultimate.component

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.myxlultimate.component.fragment.BeliBooster

class MainActivity : AppCompatActivity() {
    private val boosterFragment = BeliBooster()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.replace(R.id.MainNav, boosterFragment)

        transaction.commit()
    }
}