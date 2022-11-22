package com.example.wizardworld.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.wizardworld.R
import com.example.wizardworld.ui.home.HomeActivity

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        setLaunchScreenTimeOut()
    }

    private fun setLaunchScreenTimeOut() {
        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                val homeIntent = Intent(this, HomeActivity::class.java)
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                this.startActivity(homeIntent)
            }, TIME_OUT)
        }
    }

    companion object {
        private const val TIME_OUT: Long = 1500
    }
}