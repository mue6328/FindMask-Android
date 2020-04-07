package com.example.findmask

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        try {
//            var info : PackageInfo = packageManager.getPackageInfo("com.example.findmask", PackageManager.GET_SIGNING_CERTIFICATES)
//            for (val signature: Signature in info.signatures)
//        }



    }
}
