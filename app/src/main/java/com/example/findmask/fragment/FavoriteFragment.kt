package com.example.findmask.fragment

import android.content.Context
import android.location.Location
import android.content.DialogInterface
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.findmask.database.FavoriteDatabase
import com.example.findmask.model.MoreInfo
import java.util.ArrayList
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findmask.Utils
import com.example.findmask.adapter.FavoriteAdapter
import com.example.findmask.databinding.FragmentFavoriteBinding
import com.example.findmask.model.MaskByGeoInfo
import com.example.findmask.service.MaskService
import com.example.findmask.viewmodel.FavoriteViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteFragment : Fragment() {

    private var favoriteDatabase: FavoriteDatabase? = null
    private var favoriteList = listOf<MoreInfo>()
    private var removeList = ArrayList<MoreInfo>()
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root

        favoriteDatabase = FavoriteDatabase.getInstance(view.context)
        var favoriteAdapter = FavoriteAdapter()

        binding.favoriteRecyclerView.run {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

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

            binding.swipeRefreshLayout.setOnRefreshListener {
                favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
                favoriteViewModel.getAllFavorites().observe(viewLifecycleOwner, Observer {
                    favoriteList = it
                    MaskService.getStoreByGeoInfo(latitude, longitude, 5000)
                        .enqueue(object : Callback<MaskByGeoInfo> {
                            override fun onFailure(call: Call<MaskByGeoInfo>, t: Throwable) {

                            }
                            override fun onResponse(
                                call: Call<MaskByGeoInfo>,
                                response: Response<MaskByGeoInfo>
                            ) {
                                for (j in favoriteList.indices) {
                                    for (i in 0 until response.body()!!.count) {
                                        if (favoriteList[j].addr == response.body()!!.stores[i].addr) {
                                            favoriteList[j].remain_stat =
                                                response.body()!!.stores[i].remain_stat
                                            favoriteList[j].stock_at = response.body()!!.stores[i].stock_at
                                            favoriteList[j].created_at =
                                                response.body()!!.stores[i].created_at
                                        }
                                    }
                                }
                                favoriteAdapter.setItem(favoriteList, view.context)
                            }
                        })
                })

                binding.swipeRefreshLayout.isRefreshing = false
            }

            favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
            favoriteViewModel.getAllFavorites().observe(viewLifecycleOwner, Observer {
                    favoriteList = it
                    MaskService.getStoreByGeoInfo(latitude, longitude, 5000)
                        .enqueue(object : Callback<MaskByGeoInfo> {
                            override fun onFailure(call: Call<MaskByGeoInfo>, t: Throwable) {

                            }
                            override fun onResponse(
                                call: Call<MaskByGeoInfo>,
                                response: Response<MaskByGeoInfo>
                            ) {
                                for (j in favoriteList.indices) {
                                    for (i in 0 until response.body()!!.count) {
                                        if (favoriteList[j].addr == response.body()!!.stores[i].addr) {
                                            favoriteList[j].remain_stat =
                                                response.body()!!.stores[i].remain_stat
                                            favoriteList[j].stock_at = response.body()!!.stores[i].stock_at
                                            favoriteList[j].created_at =
                                                response.body()!!.stores[i].created_at
                                        }
                                    }
                                }
                                favoriteAdapter.setItem(favoriteList, view.context)
                            }
                        })
            })

//            MaskService.getStoreByGeoInfo(latitude, longitude, 5000)
//                .enqueue(object : Callback<MaskByGeoInfo> {
//                    override fun onFailure(call: Call<MaskByGeoInfo>, t: Throwable) {
//
//                    }
//
//                    override fun onResponse(
//                        call: Call<MaskByGeoInfo>,
//                        response: Response<MaskByGeoInfo>
//                    ) {
//                        for (j in favoriteList.indices) {
//                            for (i in 0 until response.body()!!.count) {
//                                if (favoriteList[j].addr == response.body()!!.stores[i].addr) {
//                                    favoriteList[j].remain_stat =
//                                        response.body()!!.stores[i].remain_stat
//                                    favoriteList[j].stock_at = response.body()!!.stores[i].stock_at
//                                    favoriteList[j].created_at =
//                                        response.body()!!.stores[i].created_at
//                                }
//                            }
//                        }
//                        favoriteAdapter.setItem(favoriteList, view.context)
//                    }
//                })
        } catch (e: SecurityException) {
            e.printStackTrace()
        }

        binding.deleteAll.setOnClickListener {
            if (favoriteList.isEmpty()) {
            } else {
                AlertDialog.Builder(view.context)
                    .setMessage("전체 삭제하시겠습니까?")
                    .setPositiveButton(
                        "예"
                    ) { d: DialogInterface?, w: Int ->
                        favoriteViewModel.deleteAll()
                        favoriteAdapter.setItem(removeList, view.context)
                    }
                    .setNegativeButton(
                        "아니오"
                    ) { d: DialogInterface?, w: Int -> }
                    .create()
                    .show()
            }
        }
        return view
    }
}