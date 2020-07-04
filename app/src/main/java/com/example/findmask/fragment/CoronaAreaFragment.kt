package com.example.findmask.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.findmask.Utils
import com.example.findmask.databinding.FragmentCoronaAreaBinding
import com.example.findmask.model.CoronaInfoNew
import com.example.findmask.service.CoronaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findmask.adapter.CoronaAreaAdapter
import com.example.findmask.database.CoronaAreaDatabase
import com.example.findmask.model.CoronaArea
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.findmask.viewmodel.CoronaAreaViewModel

class CoronaAreaFragment : Fragment() {

    private var coronaAreaDatabase: CoronaAreaDatabase? = null
    private lateinit var coronaAreaViewModel: CoronaAreaViewModel
    private var coronaAreaAdapter = CoronaAreaAdapter()
    private lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCoronaAreaBinding.inflate(inflater, container, false)
        val view = binding.root

        var removeList = listOf<CoronaArea>()

        coronaAreaDatabase = CoronaAreaDatabase.getInstance(view.context)

        binding.areaRecyclerView.run {
            adapter = coronaAreaAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        var country: String

        val items: Array<CharSequence> = arrayOf(
            "서울", "부산", "대구", "인천", "광주", "대전"
            , "울산", "세종", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "제주", "검역"
        )

        val builder = AlertDialog.Builder(view.context)

        builder.setTitle("추가 할 지역을 선택하세요.")
        builder.setItems(items) { _, i ->
            country = items[i].toString()
            CoronaService.getCoronaInfoNew(Utils.API_KEY).enqueue(object : Callback<CoronaInfoNew> {
                override fun onFailure(call: Call<CoronaInfoNew>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<CoronaInfoNew>,
                    response: Response<CoronaInfoNew>
                ) {
                    lateinit var coronaArea: CoronaArea
                    if (country.equals(response.body()!!.seoul.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.seoul.countryName,
                            response.body()!!.seoul.newCase,
                            response.body()!!.seoul.newFcase,
                            response.body()!!.seoul.newCcase,
                            response.body()!!.seoul.totalCase,
                            response.body()!!.seoul.recovered,
                            response.body()!!.seoul.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.daegu.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.daegu.countryName,
                            response.body()!!.daegu.newCase,
                            response.body()!!.daegu.newFcase,
                            response.body()!!.daegu.newCcase,
                            response.body()!!.daegu.totalCase,
                            response.body()!!.daegu.recovered,
                            response.body()!!.daegu.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.busan.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.busan.countryName,
                            response.body()!!.busan.newCase,
                            response.body()!!.busan.newFcase,
                            response.body()!!.busan.newCcase,
                            response.body()!!.busan.totalCase,
                            response.body()!!.busan.recovered,
                            response.body()!!.busan.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.incheon.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.incheon.countryName,
                            response.body()!!.incheon.newCase,
                            response.body()!!.incheon.newFcase,
                            response.body()!!.incheon.newCcase,
                            response.body()!!.incheon.totalCase,
                            response.body()!!.incheon.recovered,
                            response.body()!!.incheon.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.gwangju.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.gwangju.countryName,
                            response.body()!!.gwangju.newCase,
                            response.body()!!.gwangju.newFcase,
                            response.body()!!.gwangju.newCcase,
                            response.body()!!.gwangju.totalCase,
                            response.body()!!.gwangju.recovered,
                            response.body()!!.gwangju.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.daejeon.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.daejeon.countryName,
                            response.body()!!.daejeon.newCase,
                            response.body()!!.daejeon.newFcase,
                            response.body()!!.daejeon.newCcase,
                            response.body()!!.daejeon.totalCase,
                            response.body()!!.daejeon.recovered,
                            response.body()!!.daejeon.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.ulsan.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.ulsan.countryName,
                            response.body()!!.ulsan.newCase,
                            response.body()!!.ulsan.newFcase,
                            response.body()!!.ulsan.newCcase,
                            response.body()!!.ulsan.totalCase,
                            response.body()!!.ulsan.recovered,
                            response.body()!!.ulsan.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.sejong.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.sejong.countryName,
                            response.body()!!.sejong.newCase,
                            response.body()!!.sejong.newFcase,
                            response.body()!!.sejong.newCcase,
                            response.body()!!.sejong.totalCase,
                            response.body()!!.sejong.recovered,
                            response.body()!!.sejong.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.gyeonggi.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.gyeonggi.countryName,
                            response.body()!!.gyeonggi.newCase,
                            response.body()!!.gyeonggi.newFcase,
                            response.body()!!.gyeonggi.newCcase,
                            response.body()!!.gyeonggi.totalCase,
                            response.body()!!.gyeonggi.recovered,
                            response.body()!!.gyeonggi.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.gangwon.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.gangwon.countryName,
                            response.body()!!.gangwon.newCase,
                            response.body()!!.gangwon.newFcase,
                            response.body()!!.gangwon.newCcase,
                            response.body()!!.gangwon.totalCase,
                            response.body()!!.gangwon.recovered,
                            response.body()!!.gangwon.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.chungbuk.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.chungbuk.countryName,
                            response.body()!!.chungbuk.newCase,
                            response.body()!!.chungbuk.newFcase,
                            response.body()!!.chungbuk.newCcase,
                            response.body()!!.chungbuk.totalCase,
                            response.body()!!.chungbuk.recovered,
                            response.body()!!.chungbuk.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.chungnam.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.chungnam.countryName,
                            response.body()!!.chungnam.newCase,
                            response.body()!!.chungnam.newFcase,
                            response.body()!!.chungnam.newCcase,
                            response.body()!!.chungnam.totalCase,
                            response.body()!!.chungnam.recovered,
                            response.body()!!.chungnam.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    }
                    else if (country.equals(response.body()!!.jeonbuk.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.jeonbuk.countryName,
                            response.body()!!.jeonbuk.newCase,
                            response.body()!!.jeonbuk.newFcase,
                            response.body()!!.jeonbuk.newCcase,
                            response.body()!!.jeonbuk.totalCase,
                            response.body()!!.jeonbuk.recovered,
                            response.body()!!.jeonbuk.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    }
                    else if (country.equals(response.body()!!.jeonnam.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.jeonnam.countryName,
                            response.body()!!.jeonnam.newCase,
                            response.body()!!.jeonnam.newFcase,
                            response.body()!!.jeonnam.newCcase,
                            response.body()!!.jeonnam.totalCase,
                            response.body()!!.jeonnam.recovered,
                            response.body()!!.jeonnam.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    }
                    else if (country.equals(response.body()!!.gyeongbuk.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.gyeongbuk.countryName,
                            response.body()!!.gyeongbuk.newCase,
                            response.body()!!.gyeongbuk.newFcase,
                            response.body()!!.gyeongbuk.newCcase,
                            response.body()!!.gyeongbuk.totalCase,
                            response.body()!!.gyeongbuk.recovered,
                            response.body()!!.gyeongbuk.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.gyeongnam.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.gyeongnam.countryName,
                            response.body()!!.gyeongnam.newCase,
                            response.body()!!.gyeongnam.newFcase,
                            response.body()!!.gyeongnam.newCcase,
                            response.body()!!.gyeongnam.totalCase,
                            response.body()!!.gyeongnam.recovered,
                            response.body()!!.gyeongnam.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.gyeongbuk.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.gyeongbuk.countryName,
                            response.body()!!.gyeongbuk.newCase,
                            response.body()!!.gyeongbuk.newFcase,
                            response.body()!!.gyeongbuk.newCcase,
                            response.body()!!.gyeongbuk.totalCase,
                            response.body()!!.gyeongbuk.recovered,
                            response.body()!!.gyeongbuk.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.jeju.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.jeju.countryName,
                            response.body()!!.jeju.newCase,
                            response.body()!!.jeju.newFcase,
                            response.body()!!.jeju.newCcase,
                            response.body()!!.jeju.totalCase,
                            response.body()!!.jeju.recovered,
                            response.body()!!.jeju.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    } else if (country.equals(response.body()!!.quarantine.countryName)) {
                        coronaArea = CoronaArea(
                            response.body()!!.quarantine.countryName,
                            response.body()!!.quarantine.newCase,
                            response.body()!!.quarantine.newFcase,
                            response.body()!!.quarantine.newCcase,
                            response.body()!!.quarantine.totalCase,
                            response.body()!!.quarantine.recovered,
                            response.body()!!.quarantine.death
                        )
                        coronaAreaAdapter.addItem(coronaArea)
                    }
                    Log.d("지역", "" + items[i])

                }
            })
        }

        dialog = builder.create()

        coronaAreaViewModel = ViewModelProvider(this).get(CoronaAreaViewModel::class.java)
        coronaAreaViewModel.getAllAreas().observe(viewLifecycleOwner, Observer {
            coronaAreaAdapter.setItem(it, view.context)
            CoronaService.getCoronaInfoNew(Utils.API_KEY).enqueue(object : Callback<CoronaInfoNew> {
                override fun onFailure(call: Call<CoronaInfoNew>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<CoronaInfoNew>,
                    response: Response<CoronaInfoNew>
                ) {
                    lateinit var coronaArea: CoronaArea
                    for (i in it.indices) {
                        if (it[i].areaName.equals(response.body()!!.seoul.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.seoul.countryName,
                                response.body()!!.seoul.newCase,
                                response.body()!!.seoul.newFcase,
                                response.body()!!.seoul.newCcase,
                                response.body()!!.seoul.totalCase,
                                response.body()!!.seoul.recovered,
                                response.body()!!.seoul.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.daegu.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.daegu.countryName,
                                response.body()!!.daegu.newCase,
                                response.body()!!.daegu.newFcase,
                                response.body()!!.daegu.newCcase,
                                response.body()!!.daegu.totalCase,
                                response.body()!!.daegu.recovered,
                                response.body()!!.daegu.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.busan.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.busan.countryName,
                                response.body()!!.busan.newCase,
                                response.body()!!.busan.newFcase,
                                response.body()!!.busan.newCcase,
                                response.body()!!.busan.totalCase,
                                response.body()!!.busan.recovered,
                                response.body()!!.busan.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.incheon.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.incheon.countryName,
                                response.body()!!.incheon.newCase,
                                response.body()!!.incheon.newFcase,
                                response.body()!!.incheon.newCcase,
                                response.body()!!.incheon.totalCase,
                                response.body()!!.incheon.recovered,
                                response.body()!!.incheon.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.gwangju.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.gwangju.countryName,
                                response.body()!!.gwangju.newCase,
                                response.body()!!.gwangju.newFcase,
                                response.body()!!.gwangju.newCcase,
                                response.body()!!.gwangju.totalCase,
                                response.body()!!.gwangju.recovered,
                                response.body()!!.gwangju.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.daejeon.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.daejeon.countryName,
                                response.body()!!.daejeon.newCase,
                                response.body()!!.daejeon.newFcase,
                                response.body()!!.daejeon.newCcase,
                                response.body()!!.daejeon.totalCase,
                                response.body()!!.daejeon.recovered,
                                response.body()!!.daejeon.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.ulsan.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.ulsan.countryName,
                                response.body()!!.ulsan.newCase,
                                response.body()!!.ulsan.newFcase,
                                response.body()!!.ulsan.newCcase,
                                response.body()!!.ulsan.totalCase,
                                response.body()!!.ulsan.recovered,
                                response.body()!!.ulsan.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.sejong.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.sejong.countryName,
                                response.body()!!.sejong.newCase,
                                response.body()!!.sejong.newFcase,
                                response.body()!!.sejong.newCcase,
                                response.body()!!.sejong.totalCase,
                                response.body()!!.sejong.recovered,
                                response.body()!!.sejong.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.gyeonggi.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.gyeonggi.countryName,
                                response.body()!!.gyeonggi.newCase,
                                response.body()!!.gyeonggi.newFcase,
                                response.body()!!.gyeonggi.newCcase,
                                response.body()!!.gyeonggi.totalCase,
                                response.body()!!.gyeonggi.recovered,
                                response.body()!!.gyeonggi.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.gangwon.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.gangwon.countryName,
                                response.body()!!.gangwon.newCase,
                                response.body()!!.gangwon.newFcase,
                                response.body()!!.gangwon.newCcase,
                                response.body()!!.gangwon.totalCase,
                                response.body()!!.gangwon.recovered,
                                response.body()!!.gangwon.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.chungbuk.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.chungbuk.countryName,
                                response.body()!!.chungbuk.newCase,
                                response.body()!!.chungbuk.newFcase,
                                response.body()!!.chungbuk.newCcase,
                                response.body()!!.chungbuk.totalCase,
                                response.body()!!.chungbuk.recovered,
                                response.body()!!.chungbuk.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.chungnam.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.chungnam.countryName,
                                response.body()!!.chungnam.newCase,
                                response.body()!!.chungnam.newFcase,
                                response.body()!!.chungnam.newCcase,
                                response.body()!!.chungnam.totalCase,
                                response.body()!!.chungnam.recovered,
                                response.body()!!.chungnam.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.jeonbuk.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.jeonbuk.countryName,
                                response.body()!!.jeonbuk.newCase,
                                response.body()!!.jeonbuk.newFcase,
                                response.body()!!.jeonbuk.newCcase,
                                response.body()!!.jeonbuk.totalCase,
                                response.body()!!.jeonbuk.recovered,
                                response.body()!!.jeonbuk.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.jeonnam.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.jeonnam.countryName,
                                response.body()!!.jeonnam.newCase,
                                response.body()!!.jeonnam.newFcase,
                                response.body()!!.jeonnam.newCcase,
                                response.body()!!.jeonnam.totalCase,
                                response.body()!!.jeonnam.recovered,
                                response.body()!!.jeonnam.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.gyeongbuk.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.gyeongbuk.countryName,
                                response.body()!!.gyeongbuk.newCase,
                                response.body()!!.gyeongbuk.newFcase,
                                response.body()!!.gyeongbuk.newCcase,
                                response.body()!!.gyeongbuk.totalCase,
                                response.body()!!.gyeongbuk.recovered,
                                response.body()!!.gyeongbuk.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.gyeongnam.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.gyeongnam.countryName,
                                response.body()!!.gyeongnam.newCase,
                                response.body()!!.gyeongnam.newFcase,
                                response.body()!!.gyeongnam.newCcase,
                                response.body()!!.gyeongnam.totalCase,
                                response.body()!!.gyeongnam.recovered,
                                response.body()!!.gyeongnam.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.gyeongbuk.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.gyeongbuk.countryName,
                                response.body()!!.gyeongbuk.newCase,
                                response.body()!!.gyeongbuk.newFcase,
                                response.body()!!.gyeongbuk.newCcase,
                                response.body()!!.gyeongbuk.totalCase,
                                response.body()!!.gyeongbuk.recovered,
                                response.body()!!.gyeongbuk.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.jeju.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.jeju.countryName,
                                response.body()!!.jeju.newCase,
                                response.body()!!.jeju.newFcase,
                                response.body()!!.jeju.newCcase,
                                response.body()!!.jeju.totalCase,
                                response.body()!!.jeju.recovered,
                                response.body()!!.jeju.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        } else if (it[i].areaName.equals(response.body()!!.quarantine.countryName)) {
                            coronaArea = CoronaArea(
                                response.body()!!.quarantine.countryName,
                                response.body()!!.quarantine.newCase,
                                response.body()!!.quarantine.newFcase,
                                response.body()!!.quarantine.newCcase,
                                response.body()!!.quarantine.totalCase,
                                response.body()!!.quarantine.recovered,
                                response.body()!!.quarantine.death
                            )
                            coronaAreaAdapter.addItem(coronaArea)
                        }
                        Log.d("지역", "" + items[i])
                    }
                }
            })


        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            coronaAreaViewModel.getAllAreas().observe(viewLifecycleOwner, Observer {
                coronaAreaAdapter.setItem(it, view.context)
            })

            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.deleteAll.setOnClickListener {
            if (coronaAreaViewModel.getAllAreas() == null) {
            } else {
                AlertDialog.Builder(view.context)
                    .setMessage("전체 삭제하시겠습니까?")
                    .setPositiveButton(
                        "예"
                    ) { d: DialogInterface?, w: Int ->
                        coronaAreaViewModel.deleteAll()
                        coronaAreaAdapter.setItem(removeList, view.context)
                    }
                    .setNegativeButton(
                        "아니오"
                    ) { d: DialogInterface?, w: Int -> }
                    .create()
                    .show()
            }
        }

        binding.addAreaButton.setOnClickListener {
            dialog.show()
        }
        return view
    }
}