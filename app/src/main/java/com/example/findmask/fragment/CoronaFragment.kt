package com.example.findmask.fragment

import android.graphics.Color
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
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_corona.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class CoronaFragment : Fragment() {

    private var coronaService: CoronaService? = null

    private var colors = ArrayList<Int>()

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

        var pieChart = view.findViewById<PieChart>(R.id.pieChart)

        var entry = ArrayList<PieEntry>()

        colors.add(Color.rgb(80, 188, 223))
        colors.add(Color.rgb(211, 211, 211))
        colors.add(Color.GRAY)
        colors.add(Color.DKGRAY)
        colors.add(Color.LTGRAY)

            coronaService!!.getCoronaInfo(Utils.API_KEY).enqueue(object : Callback<CoronaInfo> {
                override fun onFailure(call: Call<CoronaInfo>, t: Throwable) {
                    Log.d("error", t.toString())
                }

                override fun onResponse(call: Call<CoronaInfo>, response: Response<CoronaInfo>) {
                    if (response.body() != null) {
                        Log.d("Corona", response.body().toString())
                        pieChart.setUsePercentValues(true)
                        pieChart.setExtraOffsets(5f,10f,5f,5f)

                        pieChart.isDrawHoleEnabled = false
                        pieChart.setHoleColor(Color.WHITE)
                        pieChart.transparentCircleRadius = 61f

                        entry.add(PieEntry(response.body()!!.city1p.toFloat(), response.body()!!.city1n))
                        entry.add(PieEntry(response.body()!!.city2p.toFloat(), response.body()!!.city2n))
                        entry.add(PieEntry(response.body()!!.city3p.toFloat(), response.body()!!.city3n))
                        entry.add(PieEntry(response.body()!!.city4p.toFloat(), response.body()!!.city4n))
                        entry.add(PieEntry(response.body()!!.city5p.toFloat(), response.body()!!.city5n))

                        var description = Description()
                        description.text = "시도 별 확진자 현황(%)"
                        description.textSize = 12f
                        pieChart.description = description

                        var dataset = PieDataSet(entry, "")

                        dataset.setColors(colors)

                        var pieData = PieData(dataset)
                        pieData.setValueTextSize(10f)
                        pieChart.animateXY(1000, 1000)
                        pieChart.data = pieData


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