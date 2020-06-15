package com.example.findmask.activity

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import com.example.findmask.R
import com.example.findmask.fragment.CoronaFragment
import com.example.findmask.fragment.FavoriteFragment
import com.example.findmask.fragment.MoreInfoFragment
import com.example.findmask.fragment.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val mainFragment = MainFragment()
    private val coronaFragment = CoronaFragment()
    private val moreInfoFragment = MoreInfoFragment()
    private val favoriteFragment = FavoriteFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bottomNavigationView = findViewById<View>(R.id.bottom_Navi) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        //getHashKey()

        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, mainFragment).commit()
    }


    @RequiresApi(Build.VERSION_CODES.P)
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



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_main -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, mainFragment).commitAllowingStateLoss()
            }
            R.id.action_corona -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, coronaFragment).commitAllowingStateLoss()
            }
            R.id.action_MoreInfo -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, moreInfoFragment).commitAllowingStateLoss()
            }
            R.id.action_Favorite -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, favoriteFragment).commitAllowingStateLoss()
            }
        }
        return true
    }
}
