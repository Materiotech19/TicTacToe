package com.materiotech.rajkumarnalluchamy.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.materiotech.rajkumarnalluchamy.R
import com.materiotech.rajkumarnalluchamy.fragment.SignInFragment
import java.lang.Thread.sleep




class SplashScreen : AppCompatActivity(){

    private val SPLASH_TIME :Long = 1000
    private lateinit var mRunnable:Runnable;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_splashscreen)

        mRunnable = Runnable {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, SignInFragment.newInstance())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        Handler().postDelayed(mRunnable, SPLASH_TIME)

    }
}