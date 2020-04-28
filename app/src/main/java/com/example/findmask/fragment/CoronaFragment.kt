package com.example.findmask.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IntegerRes
import androidx.fragment.app.Fragment
import com.example.findmask.R
import com.example.findmask.Utils
import com.example.findmask.model.CoronaInfo
import com.example.findmask.service.CoronaService
import com.example.findmask.service.MaskService
import kotlinx.android.synthetic.main.fragment_corona.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoronaFragment : Fragment() {

    private var coronaService: CoronaService? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_corona, container, false)
        initService()

        var totalCase = view.findViewById<TextView>(R.id.totalCase)
        var totalRecovered = view.findViewById<TextView>(R.id.totalRecovered)
        var totalNowCase = view.findViewById<TextView>(R.id.totalNowCase)
        var totalDeath = view.findViewById<TextView>(R.id.totalDeath)

        var todayRecovered = view.findViewById<TextView>(R.id.todayRecovered)
        var todayNowCase = view.findViewById<TextView>(R.id.todayNowCase)
        var todayDeath = view.findViewById<TextView>(R.id.todayDeath)

            coronaService!!.getCoronaInfo(Utils.API_KEY).enqueue(object : Callback<CoronaInfo> {
                override fun onFailure(call: Call<CoronaInfo>, t: Throwable) {
                    Log.d("error", t.toString())
                }

                override fun onResponse(call: Call<CoronaInfo>, response: Response<CoronaInfo>) {
                    if (response.body() != null) {
                        var nowCase = response.body()!!.TotalCaseBefore.toInt()

                        totalCase.text = response.body()!!.TotalCase + " 명"
                        totalRecovered.text = response.body()!!.TotalRecovered + " 명"
                        totalNowCase.text = response.body()!!.NowCase + " 명"
                        totalDeath.text = response.body()!!.TotalDeath + " 명"

                        todayRecovered.text = "전일 대비 + " + response.body()!!.TodayRecovered + "명"
                        todayNowCase.text = "전일 대비 - " + -nowCase + "명"
                        todayDeath.text = "전일 대비 + " + response.body()!!.TodayDeath + "명"
                    }
                }
            })

        return view
    }

    private fun initService() {
        coronaService = Utils.retrofit_CORONA.create(CoronaService::class.java)
    }
}