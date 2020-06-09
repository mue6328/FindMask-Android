package com.example.findmask.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.findmask.R
import java.lang.Exception

class LoadingActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        loading()
    }

    private fun loading() {
        var handler = Handler()
        handler.postDelayed(Runnable {
            startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
            finish()
        }, 2000)
    }
}