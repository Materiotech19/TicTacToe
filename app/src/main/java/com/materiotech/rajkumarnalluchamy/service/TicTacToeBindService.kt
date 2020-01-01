package com.materiotech.rajkumarnalluchamy.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class TicTacToeBindService(mContext: Context) : Service(){

    private val myBinder = LocalBinder()
    override fun onBind(intent: Intent?): IBinder? {
        return myBinder
    }

    inner class LocalBinder: Binder(){
        fun getService() : TicTacToeBindService {
            return this@TicTacToeBindService
        }
    }

    fun getTheFirstItem(position: Int){

    }
}