package com.example.findmask.fragment

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.findmask.R
import com.example.findmask.Utils
import com.example.findmask.model.CoronaInfo
import com.example.findmask.model.MaskByGeoInfo
import com.example.findmask.service.CoronaService
import com.example.findmask.service.MaskService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainFragment : Fragment() {

    private var maskService: MaskService? = null
    private var coronaService: CoronaService? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false);

        var coronaButton = view.findViewById<Button>(R.id.coronaButton)
        var gpsButton = view.findViewById<Button>(R.id.gpsButton)

        val activity = activity
        initService()

        coronaButton.setOnClickListener {
            coronaService!!.getCoronaInfo(Utils.API_KEY).enqueue(object : Callback<CoronaInfo>{
                override fun onFailure(call: Call<CoronaInfo>, t: Throwable) {
                    Log.d("error", t.toString())
                }

                override fun onResponse(call: Call<CoronaInfo>, response: Response<CoronaInfo>) {
                    Log.d("Corona", "" + response.body().toString() + response.message() + response.code())
                }

            })
        }

        gpsButton.setOnClickListener {
            try {
                val lm : LocationManager? = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                var location : Location? = null

                if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(activity!!.applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        0)
                }
                else {
                    location = lm!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    var longitude = location!!.longitude.toFloat()
                    var latitude = location!!.latitude.toFloat()

                    val mapView = MapView(activity)

                    val mapViewContainer = map_view as ViewGroup

                    mapViewContainer.addView(mapView)

                    maskService!!.getStoreByGeoInfo(latitude, longitude, 500).enqueue(object :
                        Callback<MaskByGeoInfo> {
                        override fun onFailure(call: Call<MaskByGeoInfo>, t: Throwable) {
                            Log.d("error",t.toString())
                        }

                        override fun onResponse(
                            call: Call<MaskByGeoInfo>,
                            response: Response<MaskByGeoInfo>
                        ) {
//                            gpsTest.setText(
//                                response.body().toString() + response.code() + response.message() +
//                                "위도: " + longitude + "\n" +
//                                        "경도: " + latitude)

//                            lm.requestLocationUpdates(
//                                LocationManager.GPS_PROVIDER,
//                                1000,
//                                1.0f,
//                                locationListener)
//                            lm.requestLocationUpdates(
//                                LocationManager.NETWORK_PROVIDER,
//                                1000,
//                                1.0f,
//                                locationListener)
                        }
                    })
                }
            }
            catch (e: SecurityException) {
                e.printStackTrace()
            }
        }

        return view
    }

    private fun initService() {
        maskService = Utils.retrofit_MASK.create(MaskService::class.java)
        coronaService = Utils.retrofit_CORONA.create(CoronaService::class.java)
    }

}