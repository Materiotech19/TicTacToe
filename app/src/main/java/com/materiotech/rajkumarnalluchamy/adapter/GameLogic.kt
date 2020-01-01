package com.materiotech.rajkumarnalluchamy.adapter

import android.content.Context
import android.os.Handler
import com.materiotech.rajkumarnalluchamy.listner.UpdateTheWidget
import java.security.SecureRandom
import android.util.Log
import java.util.*


class GameLogic(context:Context,updateTheWidget: UpdateTheWidget){

    var mUpdateTheWidget: UpdateTheWidget?=null
    var isFirst = true
    var maxValue : Int =8
    var minValue : Int =0
    var CPlayer = "Player"
    var BPlayer = "Bot"
    var delayMiles: Long = 1000
    val playerX = arrayListOf<Int>()
    val playerO = arrayListOf<Int>()
    private lateinit var mRunnable:Runnable;

    init {
        mUpdateTheWidget = updateTheWidget
    }

fun checkTheNextItem(position: Int){


    if (isFirst){
        playerX.clear()
        playerO.clear()
        var ranIndex = SecureRandom().nextInt(maxValue)
        if (ranIndex==position){
            if (ranIndex==maxValue){
                botSelection(ranIndex-1)
                isFirst=false
            }else if (ranIndex==minValue){
                botSelection(ranIndex+1)
                isFirst=false
            }
        }else{
            botSelection(ranIndex)
            isFirst=false
        }
        playerSelection(position)
    }else {
        playerSelection(position)
        mRunnable  = Runnable {
        if (!CheckForWinning(CPlayer)) {
            if(CheckForWinning(BPlayer)){
                Log.d("TAG","You Wins")
            }

        }else{
            Log.d("TAG","You Wins")
//            clearView()
        }
        }
        Handler().postDelayed(mRunnable,delayMiles)
    }

}


    private fun botSelection(position: Int){
        playerO.add(position)
//        mRunnable  = Runnable {
            //                    checkForTheOption(position)
            mUpdateTheWidget?.updateTheUI(BPlayer,position)
//        }
//        Handler().postDelayed(mRunnable,delayMiles)

    }

    fun playerSelection(position: Int){
        playerX.add(position)
        mUpdateTheWidget?.updateTheUI(CPlayer,position)
    }

    private fun getPossiblePosition(possibility:IntArray, player: String): Boolean {

        var possiblePos :Int =0
        var count:Int = 0
        var countList: ArrayList<Int> = arrayListOf()
        val winnerList = arrayListOf<Int>()
        winnerList.clear()
        if (player.equals(CPlayer)){
            winnerList.addAll(playerO)
        }else if (player.equals(BPlayer)){
            winnerList.addAll(playerX)
        }


        if (winnerList.size>1) {
            for (possible in possibility) {
                if (!isPresent(possible, winnerList)) {
                    possiblePos = possible
                } else {
                    count++
                }
            }
        }

        if (count>1){
            if (player.equals(BPlayer) && count==3){
                Log.d("WININGArrayList",
                Arrays.toString(winnerList.toArray()))
                Log.d("TAG","You Wins")
                return true
            }else if (player.equals(BPlayer) && CheckForWinningForBot(BPlayer)){

                    Log.d("TAG","Bot Wins")
//                clearView()
                    return true

            }else if (count==2 && !playerO.contains(possiblePos) &&!playerX.contains(possiblePos) && player.equals(BPlayer)) {
                if(playerO.contains(possiblePos))
                    return false

                botSelection(possiblePos)
                Log.d("TAG","NEXT")
                return true
            }
        }

        return false
    }

    fun CheckForWinning(player: String):Boolean{

        if(getPossiblePosition(intArrayOf(0,1,2),player)){
            //Set of Possible X1
            return true
        }else if(getPossiblePosition(intArrayOf(3,4,5),player)){
            //Set of Possible X2
            return true
        }else if(getPossiblePosition(intArrayOf(6,7,8),player)){
            //Set of Possible X3
            return true
        }else if(getPossiblePosition(intArrayOf(1,4,7),player)){
            //Set of Possible Y
            return true
        }else if(getPossiblePosition(intArrayOf(2,5,8),player)){
            //Set of Possible Y
            return true
        }else if(getPossiblePosition(intArrayOf(0,3,6),player)){
            //Set of Possible Y
            return true
        }else if(getPossiblePosition(intArrayOf(0,4,8),player)){
            //Set of Possible XX
            return true
        }else if(getPossiblePosition(intArrayOf(6,4,2),player)){
            //Set of Possible YY
            return true
        }
        return false
    }

    fun CheckForWinningForBot(player: String):Boolean{

        if(getCheckWinningPosition(intArrayOf(0,1,2),player)){
            //Set of Possible X1
            return true
        }else if(getCheckWinningPosition(intArrayOf(3,4,5),player)){
            //Set of Possible X2
            return true
        }else if(getCheckWinningPosition(intArrayOf(6,7,8),player)){
            //Set of Possible X3
            return true
        }else if(getCheckWinningPosition(intArrayOf(1,4,7),player)){
            //Set of Possible Y
            return true
        }else if(getCheckWinningPosition(intArrayOf(2,5,8),player)){
            //Set of Possible Y
            return true
        }else if(getCheckWinningPosition(intArrayOf(0,3,6),player)){
            //Set of Possible Y
            return true
        }else if(getCheckWinningPosition(intArrayOf(0,4,8),player)){
            //Set of Possible XX
            return true
        }else if(getCheckWinningPosition(intArrayOf(6,4,2),player)){
            //Set of Possible YY
            return true
        }
        return false
    }



    private fun isPresent(n: Int, b: ArrayList<Int>): Boolean {
        for (i in b) {
            if (n == i) {
                return true
            }
        }
        return false
    }

     fun clearView(){
        playerO.clear()
        playerX.clear()
        isFirst = true
        mUpdateTheWidget?.clearTheImageView()
    }

    private fun getCheckWinningPosition(possibility:IntArray, player: String): Boolean {
        var possiblePos :Int =0
        var count:Int = 0
        var countList: ArrayList<Int> = arrayListOf()
        val winnerList = arrayListOf<Int>()


        if (playerO.size>1) {
            for (possible in possibility) {
                if (!isPresent(possible, playerO)) {
                    possiblePos = possible
                } else {
                    count++
                }
            }
        }

        if (count>1 && count==2 && !playerO.contains(possiblePos) &&!playerX.contains(possiblePos) && player.equals(BPlayer)) {
            botSelection(possiblePos)
            return true
        }
        return false
    }

}