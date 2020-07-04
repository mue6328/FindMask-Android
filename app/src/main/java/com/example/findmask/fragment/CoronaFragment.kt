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
import com.example.findmask.databinding.FragmentCoronaBinding
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
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager
import android.content.Context
import com.example.findmask.model.CoronaInfoNew

class CoronaFragment : Fragment() {
    private var colors = ArrayList<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCoronaBinding.inflate(inflater, container, false)
        var view = binding.root

        var description = Description()
        var entry = ArrayList<PieEntry>()
        var totalCase: String? = null
        var korTotalCase: String? = null
        var todayCase: Int? = null

        addColors()
        CoronaService.getCoronaInfo(Utils.API_KEY).enqueue(object : Callback<CoronaInfo> {
            override fun onFailure(call: Call<CoronaInfo>, t: Throwable) {
                Log.d("error", t.toString())
            }

            override fun onResponse(call: Call<CoronaInfo>, response: Response<CoronaInfo>) {
                Log.d("code", "" + response.code())
                if (response.body() != null) {
                    Log.d("nowcase", response.body()!!.TotalCaseBefore)
                    setPieChart(binding.pieChart, description)

                    totalCase = response.body()!!.TotalCase.replace(",","")

                    CoronaService.getCoronaInfoNew(Utils.API_KEY).enqueue(object : Callback<CoronaInfoNew> {
                        override fun onFailure(call: Call<CoronaInfoNew>, t: Throwable) {
                            Log.d("error", t.toString())
                        }

                        override fun onResponse(call: Call<CoronaInfoNew>, response: Response<CoronaInfoNew>) {
                            Log.d("code", "" + response.code())
                            korTotalCase = response.body()!!.korea.totalCase.replace(",","")
                            if (korTotalCase!!.toInt() > totalCase!!.toInt())
                                todayCase = korTotalCase!!.toInt() - totalCase!!.toInt()
                            else if (korTotalCase!!.toInt() < totalCase!!.toInt())
                                todayCase = totalCase!!.toInt() - korTotalCase!!.toInt()
                            else
                                todayCase = totalCase!!.toInt() - korTotalCase!!.toInt()

                            Log.i("totalcase", "" + todayCase + " " + korTotalCase + " " + totalCase)
                            if (todayCase == 0)
                                binding.todayCase.text = "전일 대비 + " + response.body()!!.korea.newCase + "명"
                            else
                                binding.todayCase.text = "전일 대비 + " + todayCase + "명"
                        }
                    })
                    entry.add(
                        PieEntry(
                            response.body()!!.city1p.toFloat(),
                            response.body()!!.city1n
                        )
                    )
                    entry.add(
                        PieEntry(
                            response.body()!!.city2p.toFloat(),
                            response.body()!!.city2n
                        )
                    )
                    entry.add(
                        PieEntry(
                            response.body()!!.city3p.toFloat(),
                            response.body()!!.city3n
                        )
                    )
                    entry.add(
                        PieEntry(
                            response.body()!!.city4p.toFloat(),
                            response.body()!!.city4n
                        )
                    )
                    entry.add(
                        PieEntry(
                            response.body()!!.city5p.toFloat(),
                            response.body()!!.city5n
                        )
                    )

                    var dataset = PieDataSet(entry, "")

                    dataset.colors = colors

                    var pieData = PieData(dataset)
                    pieData.setValueTextSize(10f)
                    binding.pieChart.data = pieData

                    var nowCase = response.body()!!.TotalCaseBefore.toInt()

                    binding.totalCase.text = response.body()!!.TotalCase + " 명"
                    binding.totalRecovered.text = response.body()!!.TotalRecovered + " 명"
                    binding.totalNowCase.text = response.body()!!.NowCase + " 명"
                    binding.totalDeath.text = response.body()!!.TotalDeath + " 명"

                    binding.todayRecovered.text =
                        "전일 대비 + " + response.body()!!.TodayRecovered + "명"
                    if (nowCase > 0)
                        binding.todayNowCase.text = "전일 대비 + " + nowCase + "명"
                    else
                        binding.todayNowCase.text = "전일 대비 - " + -nowCase + "명"
                    binding.todayDeath.text = "전일 대비 + " + response.body()!!.TodayDeath + "명"
                }
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            addColors()
            CoronaService.getCoronaInfo(Utils.API_KEY).enqueue(object : Callback<CoronaInfo> {
                override fun onFailure(call: Call<CoronaInfo>, t: Throwable) {
                    Log.d("error", t.toString())
                }

                override fun onResponse(call: Call<CoronaInfo>, response: Response<CoronaInfo>) {
                    Log.d("code", "" + response.code())
                    if (response.body() != null) {
                        Log.d("nowcase", response.body()!!.TotalCaseBefore)
                        setPieChart(binding.pieChart, description)

                        totalCase = response.body()!!.TotalCase.replace(",","")

                        CoronaService.getCoronaInfoNew(Utils.API_KEY).enqueue(object : Callback<CoronaInfoNew> {
                            override fun onFailure(call: Call<CoronaInfoNew>, t: Throwable) {
                                Log.d("error", t.toString())
                            }

                            override fun onResponse(call: Call<CoronaInfoNew>, response: Response<CoronaInfoNew>) {

                                korTotalCase = response.body()!!.korea.totalCase.replace(",","")
                                if (korTotalCase!!.toInt() > totalCase!!.toInt())
                                    todayCase = korTotalCase!!.toInt() - totalCase!!.toInt()
                                else if (korTotalCase!!.toInt() < totalCase!!.toInt())
                                    todayCase = totalCase!!.toInt() - korTotalCase!!.toInt()
                                else
                                    todayCase = totalCase!!.toInt() - korTotalCase!!.toInt()

                                Log.i("totalcase", "" + todayCase + " " + korTotalCase + " " + totalCase)
                                if (todayCase == 0)
                                    binding.todayCase.text = "전일 대비 + " + response.body()!!.korea.newCase + "명"
                                else
                                    binding.todayCase.text = "전일 대비 + " + todayCase + "명"
                            }
                        })
                        entry.clear()
                        entry.add(
                            PieEntry(
                                response.body()!!.city1p.toFloat(),
                                response.body()!!.city1n
                            )
                        )
                        entry.add(
                            PieEntry(
                                response.body()!!.city2p.toFloat(),
                                response.body()!!.city2n
                            )
                        )
                        entry.add(
                            PieEntry(
                                response.body()!!.city3p.toFloat(),
                                response.body()!!.city3n
                            )
                        )
                        entry.add(
                            PieEntry(
                                response.body()!!.city4p.toFloat(),
                                response.body()!!.city4n
                            )
                        )
                        entry.add(
                            PieEntry(
                                response.body()!!.city5p.toFloat(),
                                response.body()!!.city5n
                            )
                        )

                        var dataset = PieDataSet(entry, "")

                        dataset.colors = colors

                        var pieData = PieData(dataset)
                        pieData.setValueTextSize(10f)
                        binding.pieChart.data = pieData

                        var nowCase = response.body()!!.TotalCaseBefore.toInt()

                        binding.totalCase.text = response.body()!!.TotalCase + " 명"
                        binding.totalRecovered.text = response.body()!!.TotalRecovered + " 명"
                        binding.totalNowCase.text = response.body()!!.NowCase + " 명"
                        binding.totalDeath.text = response.body()!!.TotalDeath + " 명"

                        binding.todayRecovered.text =
                            "전일 대비 + " + response.body()!!.TodayRecovered + "명"
                        if (nowCase > 0)
                            binding.todayNowCase.text = "전일 대비 + " + nowCase + "명"
                        else
                            binding.todayNowCase.text = "전일 대비 - " + -nowCase + "명"
                        binding.todayDeath.text = "전일 대비 + " + response.body()!!.TodayDeath + "명"
                    }
                }
            })

            binding.swipeRefreshLayout.isRefreshing = false
        }

        return view
    }

    private fun addColors() {
        colors.run {
            add(Color.rgb(80, 188, 223))
            add(Color.rgb(211, 211, 211))
            add(Color.GRAY)
            add(Color.DKGRAY)
            add(Color.LTGRAY)
        }
    }

    private fun setPieChart(pieChart: PieChart, description: Description) {
        pieChart.run {
            setUsePercentValues(true)
            setExtraOffsets(5f, 10f, 5f, 5f)
            isDrawHoleEnabled = false
            setHoleColor(Color.WHITE)
            transparentCircleRadius = 61f
            animateXY(1000, 1000)
        }
        description.run {
            text = "시도 별 확진자 현황(%)"
            textSize = 12f
        }

        pieChart.description = description
    }
}