package com.example.findmask.fragment

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
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
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainFragment : Fragment() {

    private var maskService: MaskService? = null

   // private var gpstracker: GpsTracker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false);

        var map = view.findViewById<RelativeLayout>(R.id.map_view)
        var clear = view.findViewById<ImageView>(R.id.clear)
        var centerPoint = view.findViewById<ImageView>(R.id.centerPoint)
        val activity = activity
        initService()


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
                    location = lm!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    var longitude = location!!.longitude
                    var latitude = location!!.latitude

                    var g: Geocoder = Geocoder(activity.applicationContext)

                    clear.setOnClickListener {
                        mask_cardView.visibility = View.GONE
                    }

                    val mapView = MapView(activity)

                    val mapViewContainer = map as ViewGroup

                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true)

                    mapViewContainer.addView(mapView)

                    Log.d("gps", "위도: " + latitude + " 경도: " + longitude + g.getFromLocation(latitude, longitude, 10))

                    var marker = MapPOIItem()
                    marker.itemName = "현재 위치"
                    marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
                    marker.customImageResourceId = R.drawable.baseline_fiber_manual_record_black_24dp
                    marker.markerType = MapPOIItem.MarkerType.CustomImage

                    mapView.addPOIItem(marker)

                    centerPoint.setOnClickListener {
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true)
                    }

                    maskService!!.getStoreByGeoInfo(latitude, longitude, 5000).enqueue(object :
                        Callback<MaskByGeoInfo> {
                        override fun onFailure(call: Call<MaskByGeoInfo>, t: Throwable) {
                            Log.d("error",t.toString())
                        }

                        override fun onResponse(
                            call: Call<MaskByGeoInfo>,
                            response: Response<MaskByGeoInfo>
                        ) {
                            for (i in 0 until response.body()!!.count) {
                                var marker = MapPOIItem()
                                var remain_stat: String
                                if (response.body()!!.stores[i].remain_stat == "plenty") {
                                    marker.markerType = MapPOIItem.MarkerType.BluePin
                                    remain_stat = "충분"
                                } else if (response.body()!!.stores[i].remain_stat == "some") {
                                    marker.markerType = MapPOIItem.MarkerType.YellowPin
                                    remain_stat = "보통"
                                } else if (response.body()!!.stores[i].remain_stat == "few") {
                                    marker.markerType = MapPOIItem.MarkerType.RedPin
                                    remain_stat = "부족"
                                } else if (response.body()!!.stores[i].remain_stat == "empty") {
                                    marker.markerType = MapPOIItem.MarkerType.CustomImage
                                    remain_stat = "없음"
                                } else {
                                    marker.markerType = MapPOIItem.MarkerType.CustomImage
                                    remain_stat = "판매중지"
                                }
                                marker.itemName =
                                    remain_stat + " / " + response.body()!!.stores[i].name
                                marker.mapPoint = MapPoint.mapPointWithGeoCoord(
                                    response.body()!!.stores[i].lat.toDouble(),
                                    response.body()!!.stores[i].lng.toDouble()
                                )
                                marker.customImageResourceId = R.drawable.baseline_room_black_36dp

                                mapView.addPOIItem(marker)
                            }
                        }
                        })

                            val locationListener: LocationListener = object : LocationListener {
                                override fun onLocationChanged(location: Location?) {
                                    var longitude = location!!.longitude
                                    var latitude = location!!.latitude

                                    Log.d("위도, 경도: ",  "" + latitude + longitude)
//                                    Toast.makeText(context, "위도, 경도: " + latitude + " " + longitude, Toast.LENGTH_SHORT).show()
                                    marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
                                }

                                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

                                }

                                override fun onProviderEnabled(p0: String?) {

                                }

                                override fun onProviderDisabled(p0: String?) {

                                }

                            }

                    lm.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        1000,
                        1.0f,
                        locationListener)
                    lm.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        1000,
                        1.0f,
                        locationListener)

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
            }
            catch (e: SecurityException) {
                e.printStackTrace()
            }

        return view
    }



    private fun initService() {
        maskService = Utils.retrofit_MASK.create(MaskService::class.java)
    }

}