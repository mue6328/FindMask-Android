package com.example.findmask.fragment

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.findmask.R
import com.example.findmask.database.FavoriteDatabase
import com.example.findmask.model.MoreInfo
import java.util.ArrayList
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findmask.Utils
import com.example.findmask.adapter.FavoriteAdapter
import com.example.findmask.model.MaskByGeoInfo
import com.example.findmask.service.MaskService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteFragment : Fragment() {

    private var favoriteDatabase: FavoriteDatabase? = null
    private var favoriteList = listOf<MoreInfo>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_favorite, container, false)

        favoriteDatabase = FavoriteDatabase.getInstance(view.context)

        var favoriteRecyclerView = view.findViewById<RecyclerView>(R.id.favoriteRecyclerView)
        //var toolbar = view.findViewById<Toolbar>(R.id.toolbar)


        var favoriteAdapter = FavoriteAdapter()

        favoriteRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        favoriteRecyclerView.setHasFixedSize(true)
        favoriteRecyclerView.adapter = favoriteAdapter

        val activity = activity
        //(activity as AppCompatActivity).setSupportActionBar(toolbar)

        //var actionBar = activity!!.actionBar
        //getActivity().actionBar
        //var actionBar = activity.actionBar
        //actionBar!!.title = "ee"

        val runnable = Runnable {
                favoriteList = favoriteDatabase?.favoriteDao()?.getFavorites()!!
        }

        val thread = Thread(runnable)
        thread.start()

        try {
            val lm: LocationManager? =
                activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var location: Location? = null

                location = lm!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                // 휴대폰
//                var longitude = location!!.longitude
//                var latitude = location!!.latitude

                // 에뮬레이터 테스트
                var longitude = 127.0342169
                var latitude = 37.5010881

                //var longitude = 128.568975
                //var latitude = 35.8438071

            MaskService.getStoreByGeoInfo(latitude, longitude, 500).enqueue(object : Callback<MaskByGeoInfo>{
                override fun onFailure(call: Call<MaskByGeoInfo>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<MaskByGeoInfo>,
                    response: Response<MaskByGeoInfo>
                ) {
                    for (j in favoriteList.indices) {
                        for (i in 0 until response.body()!!.count) {
                            if (favoriteList[j].addr == response.body()!!.stores[i].addr) {
                                favoriteList[j].remain_stat = response.body()!!.stores[i].remain_stat
                                favoriteList[j].stock_at = response.body()!!.stores[i].stock_at
                                favoriteList[j].created_at = response.body()!!.stores[i].created_at
                            }
                        }
                    }
                    favoriteAdapter.setItem(favoriteList, view.context)
                }
            })
        } catch (e: SecurityException) {
            e.printStackTrace()
        }

        return view
    }
}