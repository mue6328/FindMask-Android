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
import com.example.findmask.databinding.FragmentMainBinding
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
    private var mcontext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        val activity = activity

        var shopMarker = MapPOIItem()
        var remain_stat: String

        binding.clear.setOnClickListener {
            mask_cardView.visibility = View.GONE
        }

            try {
                val lm : LocationManager? = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
                var location : Location? = null

                if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(requireActivity().applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (activity != null) {
                        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                            0)
                    }
                }
                else {
                    location = lm!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    // 에뮬레이터 테스트
                    //var longitude = 127.0342169
                    //var latitude = 37.5010881

                    // 휴대폰
                    var longitude = location!!.longitude
                    var latitude = location!!.latitude

                    val mapView = MapView(activity)

                    val mapViewContainer = binding.mapView as ViewGroup

                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true)

                    mapViewContainer.addView(mapView)

                    var marker = MapPOIItem()
                    marker.itemName = "현재 위치"
                    marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
                    marker.customImageResourceId = R.drawable.baseline_fiber_manual_record_black_24dp
                    marker.markerType = MapPOIItem.MarkerType.CustomImage

                    MaskService.getStoreByGeoInfo(latitude, longitude, 5000).enqueue(object :
                        Callback<MaskByGeoInfo> {
                        override fun onFailure(call: Call<MaskByGeoInfo>, t: Throwable) {
                            Log.d("error",t.toString())
                        }

                        override fun onResponse(
                            call: Call<MaskByGeoInfo>,
                            response: Response<MaskByGeoInfo>
                        ) {
                            for (i in 0 until response.body()!!.count) {
                                if (response.body()!!.stores[i].remain_stat == "plenty") {
                                    shopMarker.markerType = MapPOIItem.MarkerType.BluePin
                                    remain_stat = "충분"
                                } else if (response.body()!!.stores[i].remain_stat == "some") {
                                    shopMarker.markerType = MapPOIItem.MarkerType.YellowPin
                                    remain_stat = "보통"
                                } else if (response.body()!!.stores[i].remain_stat == "few") {
                                    shopMarker.markerType = MapPOIItem.MarkerType.RedPin
                                    remain_stat = "부족"
                                } else if (response.body()!!.stores[i].remain_stat == "empty") {
                                    shopMarker.markerType = MapPOIItem.MarkerType.CustomImage
                                    remain_stat = "없음"
                                } else {
                                    shopMarker.markerType = MapPOIItem.MarkerType.CustomImage
                                    remain_stat = "판매중지"
                                }

                                shopMarker.itemName =
                                    remain_stat + " / " + response.body()!!.stores[i].name
                                shopMarker.mapPoint = MapPoint.mapPointWithGeoCoord(
                                    response.body()!!.stores[i].lat.toDouble(),
                                    response.body()!!.stores[i].lng.toDouble()
                                )
                                shopMarker.customImageResourceId = R.drawable.baseline_room_black_36dp

                                mapView.addPOIItem(shopMarker)
                            }
                        }
                        })

                            val locationListener: LocationListener = object : LocationListener {
                                override fun onLocationChanged(location: Location?) {
                                    if (location != null) {
                                        longitude = location!!.longitude
                                        latitude = location!!.latitude

                                        mapView.removePOIItem(marker)

                                        marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
                                        mapView.addPOIItem(marker)
                                    }
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
                        1f,
                        locationListener)
                    lm.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        1000,
                        1f,
                        locationListener)

                    binding.centerPoint.setOnClickListener {
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true)
                    }
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }

        return view
    }
}