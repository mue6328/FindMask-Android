package com.example.findmask.activity

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.example.findmask.R
import com.example.findmask.fragment.CoronaFragment
import com.example.findmask.fragment.FragmentMoreInfo
import com.example.findmask.fragment.MainFragment
import com.example.findmask.service.CoronaService
import com.example.findmask.service.MaskService
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var maskService: MaskService? = null
    private var coronaService: CoronaService? = null

    private val mainFragment = MainFragment()
    private val coronaFragment = CoronaFragment()
    private val fragment3 = FragmentMoreInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bottomNavigationView = findViewById<View>(R.id.bottom_Navi) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        getHashKey()

        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, mainFragment).commit()
    }


    private fun getHashKey() {
        try {
            var info : PackageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            val signatures = info.signingInfo.apkContentsSigners
            for (signature in signatures) {
                var md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash: ", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_main -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, mainFragment).commit()
            }
            R.id.action_corona -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, coronaFragment).commit()
            }
            R.id.action_MoreInfo -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment3).commit()
            }
        }
        return true
    }
}
