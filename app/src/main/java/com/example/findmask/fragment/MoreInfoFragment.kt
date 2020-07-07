package com.example.findmask.fragment

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findmask.R
import com.example.findmask.Utils
import com.example.findmask.adapter.MoreInfoAdapter
import com.example.findmask.model.MaskByGeoInfo
import com.example.findmask.model.MoreInfo
import com.example.findmask.service.MaskService
import kotlinx.android.synthetic.main.fragment_moreinfo.*
import net.daum.mf.map.api.MapPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList
import android.widget.EditText
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Visibility
import com.example.findmask.database.FavoriteDatabase
import com.example.findmask.databinding.FragmentMoreinfoBinding
import com.example.findmask.viewmodel.FavoriteViewModel
import kotlin.collections.HashSet

class MoreInfoFragment : Fragment() {

    override fun onResume() {
        super.onResume()

        search_filter.text.clear()
    }

    private var moreInfoList = ArrayList<MoreInfo>()
    private var moreInfoListFavorite = ArrayList<MoreInfo>()

    private var favoriteList = listOf<MoreInfo>()

    private lateinit var favoriteViewModel: FavoriteViewModel

    private var favoriteDatabase: FavoriteDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoreinfoBinding.inflate(inflater, container, false)
        val view = binding.root

        var m: Int = 500

        var moreInfoAdapter = MoreInfoAdapter()

        val activity = activity

        binding.moreInfoRecyclerView.run {
            adapter = moreInfoAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        favoriteDatabase = FavoriteDatabase.getInstance(view.context)

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favoriteViewModel.getAllFavorites().observe(viewLifecycleOwner, Observer {
            favoriteList = it
        })



        binding.searchFilter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                moreInfoAdapter.filter(binding.searchFilter.text.toString().toLowerCase())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        try {
            val lm: LocationManager? =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var location: Location? = null

            location = lm!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            // 휴대폰
//            var longitude = location!!.longitude
//            var latitude = location!!.latitude

            // 에뮬레이터 테스트
                var longitude = 127.0342169
                var latitude = 37.5010881

//                var longitude = 128.568975
//                var latitude = 35.8438071

            var isfavorite: Boolean = false

            MaskService.getStoreByGeoInfo(latitude, longitude, m)
                .enqueue(object : Callback<MaskByGeoInfo> {
                    override fun onFailure(call: Call<MaskByGeoInfo>, t: Throwable) {
                        Log.d("error", t.toString())
                    }

                    override fun onResponse(
                        call: Call<MaskByGeoInfo>,
                        response: Response<MaskByGeoInfo>
                    ) {
                        moreInfoList.clear()
                        moreInfoListFavorite.clear()
                        if (response.body()!!.stores.isEmpty() && response.code() == 200) {
                            binding.searchFilter.visibility = View.GONE
                            binding.maskSellInfo.visibility = View.VISIBLE
                            binding.research.visibility = View.VISIBLE
                        } else {
                            binding.searchFilter.visibility = View.VISIBLE
                            if (favoriteList.isNotEmpty()) { // 즐겨찾기 된 항목들 추가
                                for (j in favoriteList.indices) {
                                    for (i in 0 until response.body()!!.count) {
                                        isfavorite =
                                            favoriteList[j].addr == response.body()!!.stores[i].addr
                                        if (isfavorite && response.body()!!.stores[i].remain_stat != null) {
                                            moreInfoListFavorite.add(
                                                MoreInfo(
                                                    response.body()!!.stores[i].name,
                                                    response.body()!!.stores[i].addr,
                                                    response.body()!!.stores[i].remain_stat,
                                                    response.body()!!.stores[i].stock_at,
                                                    response.body()!!.stores[i].created_at,
                                                    isfavorite
                                                )
                                            )
                                            continue
                                        }
                                    }
                                }
                            } else { // favoriteList가 null일때
                                for (i in 0 until response.body()!!.count) {
                                    if (response.body()!!.stores[i].remain_stat != null) {
                                        moreInfoList.add(
                                            MoreInfo(
                                                response.body()!!.stores[i].name,
                                                response.body()!!.stores[i].addr,
                                                response.body()!!.stores[i].remain_stat,
                                                response.body()!!.stores[i].stock_at,
                                                response.body()!!.stores[i].created_at,
                                                false
                                            )
                                        )
                                    }
                                }
                            }

                            for (k in 0 until response.body()!!.count) { // 즐겨찾기 되지 않은 항목들 추가
                                if (moreInfoListFavorite.isNotEmpty() && response.body()!!.stores[k].remain_stat != null) {
                                    if (!moreInfoListFavorite.contains(
                                            MoreInfo(
                                                response.body()!!.stores[k].name,
                                                response.body()!!.stores[k].addr,
                                                response.body()!!.stores[k].remain_stat,
                                                response.body()!!.stores[k].stock_at,
                                                response.body()!!.stores[k].created_at,
                                                true
                                            )
                                        )
                                    ) {
                                        moreInfoList.add(
                                            MoreInfo(
                                                response.body()!!.stores[k].name,
                                                response.body()!!.stores[k].addr,
                                                response.body()!!.stores[k].remain_stat,
                                                response.body()!!.stores[k].stock_at,
                                                response.body()!!.stores[k].created_at,
                                                false
                                            )
                                        )
                                    }
                                }
                                else if (response.body()!!.stores[k].remain_stat != null) {
                                    moreInfoList.add(
                                        MoreInfo(
                                            response.body()!!.stores[k].name,
                                            response.body()!!.stores[k].addr,
                                            response.body()!!.stores[k].remain_stat,
                                            response.body()!!.stores[k].stock_at,
                                            response.body()!!.stores[k].created_at,
                                            false
                                        )
                                    )
                                }
                            }

                            moreInfoListFavorite.addAll(moreInfoList)

                            Log.i("listsize", "" + moreInfoListFavorite.size)

                            moreInfoAdapter.setItem(moreInfoListFavorite, view.context)
                        }
                    }
                })

            binding.swipeRefreshLayout.setOnRefreshListener {
                MaskService.getStoreByGeoInfo(latitude, longitude, m)
                    .enqueue(object : Callback<MaskByGeoInfo> {
                        override fun onFailure(call: Call<MaskByGeoInfo>, t: Throwable) {
                            Log.d("error", t.toString())
                        }

                        override fun onResponse(
                            call: Call<MaskByGeoInfo>,
                            response: Response<MaskByGeoInfo>
                        ) {
                            moreInfoList.clear()
                            moreInfoListFavorite.clear()
                            if (response.body()!!.stores.isEmpty() && response.code() == 200) {
                                binding.searchFilter.visibility = View.GONE
                                binding.maskSellInfo.visibility = View.VISIBLE
                                binding.research.visibility = View.VISIBLE
                            } else {
                                binding.searchFilter.visibility = View.VISIBLE
                                if (favoriteList.isNotEmpty()) { // 즐겨찾기 된 항목들 추가
                                    for (j in favoriteList.indices) {
                                        for (i in 0 until response.body()!!.count) {
                                            isfavorite =
                                                favoriteList[j].addr == response.body()!!.stores[i].addr
                                            if (isfavorite) {
                                                moreInfoListFavorite.add(
                                                    MoreInfo(
                                                        response.body()!!.stores[i].name,
                                                        response.body()!!.stores[i].addr,
                                                        response.body()!!.stores[i].remain_stat,
                                                        response.body()!!.stores[i].stock_at,
                                                        response.body()!!.stores[i].created_at,
                                                        isfavorite
                                                    )
                                                )
                                                break
                                            }
                                        }
                                    }
                                } else { // favoriteList가 null일때
                                    for (i in 0 until response.body()!!.count) {
                                        if (response.body()!!.stores[i].remain_stat != null) {
                                            moreInfoList.add(
                                                MoreInfo(
                                                    response.body()!!.stores[i].name,
                                                    response.body()!!.stores[i].addr,
                                                    response.body()!!.stores[i].remain_stat,
                                                    response.body()!!.stores[i].stock_at,
                                                    response.body()!!.stores[i].created_at,
                                                    false
                                                )
                                            )
                                        }
                                    }
                                }

                                for (k in 0 until response.body()!!.count) { // 즐겨찾기 되지 않은 항목들 추가
                                    if (moreInfoListFavorite.isNotEmpty() && response.body()!!.stores[k].remain_stat != null) {
                                        if (!moreInfoListFavorite.contains(
                                                MoreInfo(
                                                    response.body()!!.stores[k].name,
                                                    response.body()!!.stores[k].addr,
                                                    response.body()!!.stores[k].remain_stat,
                                                    response.body()!!.stores[k].stock_at,
                                                    response.body()!!.stores[k].created_at,
                                                    true
                                                )
                                            )
                                        ) {
                                            moreInfoList.add(
                                                MoreInfo(
                                                    response.body()!!.stores[k].name,
                                                    response.body()!!.stores[k].addr,
                                                    response.body()!!.stores[k].remain_stat,
                                                    response.body()!!.stores[k].stock_at,
                                                    response.body()!!.stores[k].created_at,
                                                    false
                                                )
                                            )
                                        }
                                    }
                                    else if (response.body()!!.stores[k].remain_stat != null) {
                                        moreInfoList.add(
                                            MoreInfo(
                                                response.body()!!.stores[k].name,
                                                response.body()!!.stores[k].addr,
                                                response.body()!!.stores[k].remain_stat,
                                                response.body()!!.stores[k].stock_at,
                                                response.body()!!.stores[k].created_at,
                                                false
                                            )
                                        )
                                    }
                                }

                                moreInfoListFavorite.addAll(moreInfoList)

                                Log.i("listsize", "" + moreInfoListFavorite.size)

                                moreInfoAdapter.setItem(moreInfoListFavorite, view.context)
                            }
                        }
                })
                binding.swipeRefreshLayout.isRefreshing = false
            }


            binding.research.setOnClickListener { // 500m 내에 병원이 없을 경우 1km부터 재검색함
                if (binding.research.text.contains("1km")) {
                    m = 1000
                    binding.maskSellInfo.run {
                        visibility = View.GONE
                        text = "반경 1km내에 판매처가 없습니다."
                    }
                    binding.research.run {
                        visibility = View.GONE
                        text = "2km로 재검색"
                    }
                    binding.searchFilter.hint = "찾고 싶은 병원 명을 입력하세요. (반경 1km)"
                } else if (binding.research.text.contains("2km")) {
                    m = 2000
                    binding.maskSellInfo.run {
                        visibility = View.GONE
                        text = "반경 2km내에 판매처가 없습니다."
                    }
                    binding.research.run {
                        visibility = View.GONE
                        text = "3km로 재검색"
                    }
                    binding.searchFilter.hint = "찾고 싶은 병원 명을 입력하세요. (반경 2km)"
                } else if (binding.research.text.contains("3km")) {
                    m = 3000
                    binding.maskSellInfo.run {
                        visibility = View.GONE
                        text = "반경 3km내에 판매처가 없습니다."
                    }
                    binding.research.run {
                        visibility = View.GONE
                        text = "4km로 재검색"
                    }
                    binding.searchFilter.hint = "찾고 싶은 병원 명을 입력하세요. (반경 3km)"
                } else if (binding.research.text.contains("4km")) {
                    m = 4000
                    binding.maskSellInfo.run {
                        visibility = View.GONE
                        text = "반경 4km내에 판매처가 없습니다."
                    }
                    binding.research.run {
                        visibility = View.GONE
                        text = "5km로 재검색"
                    }
                    binding.searchFilter.hint = "찾고 싶은 병원 명을 입력하세요. (반경 4km)"
                } else if (binding.research.text.contains("5km")) {
                    m = 5000
                    binding.maskSellInfo.run {
                        visibility = View.GONE
                        text = "반경 5km내에 판매처가 없습니다."
                    }
                    binding.research.run {
                        visibility = View.GONE
                    }
                    binding.searchFilter.hint = "찾고 싶은 병원 명을 입력하세요. (반경 5km)"
                }
                MaskService.getStoreByGeoInfo(latitude, longitude, m)
                    .enqueue(object : Callback<MaskByGeoInfo> {
                        override fun onFailure(call: Call<MaskByGeoInfo>, t: Throwable) {
                            Log.d("errorq", t.toString())
                        }

                        override fun onResponse(
                            call: Call<MaskByGeoInfo>,
                            response: Response<MaskByGeoInfo>
                        ) {
                            moreInfoList.clear()
                            moreInfoListFavorite.clear()
                            Log.d("resp", "" + response.code() + response.body()!!.stores)
                            if (response.body()!!.stores.isEmpty() && response.code() == 200) {
                                // response를 받아오는데 성공했지만 반경 내에 병원이 없을 때
                                binding.searchFilter.visibility = View.GONE
                                binding.maskSellInfo.visibility = View.VISIBLE
                                if (m == 5000)
                                    binding.research.visibility = View.GONE
                                else
                                    binding.research.visibility = View.VISIBLE
                            } else {
                                binding.searchFilter.visibility = View.VISIBLE
                                binding.maskSellInfo.visibility = View.GONE
                                binding.research.visibility = View.GONE
                                if (favoriteList.isNotEmpty()) {
                                    for (j in favoriteList.indices) {
                                        for (i in 0 until response.body()!!.count) {
                                            isfavorite =
                                                favoriteList[j].addr == response.body()!!.stores[i].addr
                                            if (isfavorite && response.body()!!.stores[i].remain_stat != null) {
                                                moreInfoListFavorite.add(
                                                    MoreInfo(
                                                        response.body()!!.stores[i].name,
                                                        response.body()!!.stores[i].addr,
                                                        response.body()!!.stores[i].remain_stat,
                                                        response.body()!!.stores[i].stock_at,
                                                        response.body()!!.stores[i].created_at,
                                                        isfavorite
                                                    )
                                                )
                                                break
                                            }

                                        }
                                    }
                                } else {
                                    for (i in 0 until response.body()!!.count) {
                                        if (response.body()!!.stores[i].remain_stat != null) {
                                            moreInfoList.add(
                                                MoreInfo(
                                                    response.body()!!.stores[i].name,
                                                    response.body()!!.stores[i].addr,
                                                    response.body()!!.stores[i].remain_stat,
                                                    response.body()!!.stores[i].stock_at,
                                                    response.body()!!.stores[i].created_at,
                                                    false
                                                )
                                            )
                                        }
                                    }
                                }

                                for (k in 0 until response.body()!!.count) {
                                    Log.d(
                                        "favoritesize",
                                        "" + moreInfoListFavorite.isNotEmpty() + response.body()!!.stores
                                    )
                                    if (moreInfoListFavorite.isNotEmpty() && response.body()!!.stores[k].remain_stat != null) {
                                        if (!moreInfoListFavorite.contains(
                                                MoreInfo(
                                                    response.body()!!.stores[k].name,
                                                    response.body()!!.stores[k].addr,
                                                    response.body()!!.stores[k].remain_stat,
                                                    response.body()!!.stores[k].stock_at,
                                                    response.body()!!.stores[k].created_at,
                                                    true
                                                )
                                            )
                                        ) {
                                            moreInfoList.add(
                                                MoreInfo(
                                                    response.body()!!.stores[k].name,
                                                    response.body()!!.stores[k].addr,
                                                    response.body()!!.stores[k].remain_stat,
                                                    response.body()!!.stores[k].stock_at,
                                                    response.body()!!.stores[k].created_at,
                                                    false
                                                )
                                            )
                                        }
                                    }
                                    else if (response.body()!!.stores[k].remain_stat != null) {
                                        moreInfoList.add(
                                            MoreInfo(
                                                response.body()!!.stores[k].name,
                                                response.body()!!.stores[k].addr,
                                                response.body()!!.stores[k].remain_stat,
                                                response.body()!!.stores[k].stock_at,
                                                response.body()!!.stores[k].created_at,
                                                false
                                            )
                                        )
                                    }
                                }

                                moreInfoListFavorite.addAll(moreInfoList)

                                Log.i("listsize", "" + moreInfoListFavorite.size)

                                moreInfoAdapter.setItem(moreInfoListFavorite, view.context)
                            }
                        }

                    })
            }

        } catch (e: SecurityException) {
            e.printStackTrace()
        }

        return view
    }
}