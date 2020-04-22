package com.example.findmask.activity

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.findmask.R
import com.example.findmask.Utils
import com.example.findmask.model.MaskByGeoInfo
import com.example.findmask.service.MaskService
import kotlinx.android.synthetic.main.activity_main.*
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    //var PERMISSIONS: Array<String> = {Manifest.permission}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapView = MapView(this)

        val mapViewContainer = map_view

        mapViewContainer.addView(mapView)


        //Log.d("HashKey", "Null")

//        try {
//            var info : PackageInfo = packageManager.getPackageInfo("com.example.findmask", PackageManager.GET_SIGNING_CERTIFICATES)
////            if(info == null)
////                Log.d("HashKey", "Null")
////            else
////                Log.d("HashKey", info.packageName)
//            for (signature: Signature in info!!.signatures) {
//                var md: MessageDigest = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                Log.d("KeyHash: ", Base64.encodeToString(md.digest(), Base64.DEFAULT))
//            }
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        } catch (e: NoSuchAlgorithmException) {
//            e.printStackTrace()
//        }
        var maskService: MaskService = Utils.retrofit_MASK.create(MaskService::class.java)

        gpsButton.setOnClickListener {
            try {
                val lm : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                var location : Location? = null

                if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission(applicationContext,
                            android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        0)
                }
                else {
//                    Log.d("location", location.provider)
                    location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    var provider = location!!.provider
                    var longitude = location!!.longitude.toFloat()
                    var latitude = location!!.latitude.toFloat()
                    var altitude = location!!.altitude



                    maskService.getStoreByGeoInfo(latitude, longitude, 500).enqueue(object : Callback<MaskByGeoInfo> {
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

                            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                1000,
                                1.0f,
                                locationListener)
                            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                1000,
                                1.0f,
                                locationListener)
                        }
                    })


                }
            }
            catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
        }


    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            var provider = location!!.provider
            var longitude = location!!.longitude
            var latitude = location!!.latitude
            var altitude = location!!.altitude

//            gpsTest.setText(
//                    "위도: " + longitude + "\n" +
//                    "경도: " + latitude + "\n" +
//                    "고도: " + altitude)


        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(p0: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(p0: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}
