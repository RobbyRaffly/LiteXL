package com.myxlultimate.component.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import com.myxlultimate.component.R
import com.myxlultimate.component.adapter.BoosterAdapter
import com.myxlultimate.component.model.BoosterModel
import kotlinx.android.synthetic.main.fragment_beli_booster.*

class BeliBooster : Fragment() {
    lateinit var lstDataBooster : ArrayList<BoosterModel>

    lateinit var BoosterAdapter: BoosterAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beli_booster, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lstDataBooster = ArrayList<BoosterModel>()
        BoosterAdapter = BoosterAdapter(lstDataBooster)
        getListBooster()
    }

    fun getListBooster(){
        lstDataBooster.clear()
        lstDataBooster.add(BoosterModel("PRIO Apps","Internet tanpa batas di aplikasi-aplikasi favorit Anda",R.drawable.logo_prioapps))
        lstDataBooster.add(BoosterModel("Data Bulanan","Tambah kuota internet lebih hemat dengan Bosster Data",R.drawable.logo_bulanan))
        lstDataBooster.add(BoosterModel("Data Harian","Tambah keperluan Data harian kapan saja",R.drawable.logo_hariandata))
        lstDataBooster.add(BoosterModel("OffNet Nelpon &amp; SMS","Tambah kuota Nelpon &amp; SMS ke operator lain",R.drawable.logo_offnet))
        lstDataBooster.add(BoosterModel("Data Booster Harian","Tambah kuota internet lebih hemat dengan Booster Data",R.drawable.logo_databoosterharian))
        lstDataBooster.add(BoosterModel("Data Booster Unlimited","Tambah kuota internet lebih hemat dengan Booster Data",R.drawable.logo_databoosterunlimited))
        lstDataBooster.add(BoosterModel("PRIO Bekerja &amp; Belajar","Tambah keperluan Data harian kapan saja",R.drawable.logo_priobekerjabelajar))

        BoosterAdapter.notifyDataSetChanged()




        rcvBooster.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = BoosterAdapter
        }
    }

}