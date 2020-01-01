package com.materiotech.rajkumarnalluchamy.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.materiotech.rajkumarnalluchamy.R
import com.materiotech.rajkumarnalluchamy.adapter.GameLogic
import com.materiotech.rajkumarnalluchamy.listner.UpdateTheWidget
import java.security.SecureRandom
import java.util.*



public class TicTakToeFragment : Fragment(),View.OnClickListener,
    UpdateTheWidget {

//    var size:Int?=9
    var items: Array<ImageView?>? = arrayOfNulls(9)
    var itemId : Int=0
    val playerX = arrayListOf<Int>()
    val playerO = arrayListOf<Int>()
    var player:String = "Player"
    var startButton: Button?=null
    var tikTakToeLayout :LinearLayout?=null
    var atFirst:Boolean = true
    private lateinit var mRunnable:Runnable;
    var CPlayer = "Player"
    var BPlayer = "Bot"
    var gameLogic : GameLogic?=null

//    fun newInstance(): TicTakToeFragment {
//        val args = Bundle()
////        args.putSerializable(COMIC, comic as Serializable)
//        val fragment = TicTakToeFragment()
////        fragment.arguments = args
//        return fragment
//    }

    companion object {
        fun newInstance(): TicTakToeFragment =
            TicTakToeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameLogic = GameLogic(activity!!.applicationContext,this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        var rootView = inflater?.inflate(
            R.layout.fragment_tik_tac_toe,
            container, false)
        getWidget(rootView)
        return rootView
    }

    private fun getWidget(rootView: View){

        startButton = rootView.findViewById(R.id.startButton)
        tikTakToeLayout = rootView.findViewById(R.id.tikTakToeLayout)
        val ids: IntArray = intArrayOf(R.id.imageView1,R.id.imageView2,R.id.imageView3
        , R.id.imageView4,R.id.imageView5,R.id.imageView6
        ,R.id.imageView7,R.id.imageView8,R.id.imageView9)


        startButton!!.setOnClickListener(View.OnClickListener {
            startButton!!.visibility = View.INVISIBLE
            tikTakToeLayout!!.visibility = View.VISIBLE
        })
        for (id: Int in ids) {

            items!![itemId]=rootView.findViewById(id) as ImageView
            items!![itemId]!!.setOnClickListener(this)
            itemId++

        }


//        var imageView1 = rootView.findViewById(R.id.imageView1) as ImageView
//        var imageView2 = rootView.findViewById(R.id.imageView2) as ImageView
//        var imageView3 = rootView.findViewById(R.id.imageView3) as ImageView
//        var imageView4 = rootView.findViewById(R.id.imageView4) as ImageView
//        var imageView5 = rootView.findViewById(R.id.imageView5) as ImageView
//        var imageView6 = rootView.findViewById(R.id.imageView6) as ImageView
//        var imageView7 = rootView.findViewById(R.id.imageView7) as ImageView
//        var imageView8 = rootView.findViewById(R.id.imageView8) as ImageView
//        var imageView9 = rootView.findViewById(R.id.imageView9) as ImageView
//
//        imageView1.setOnClickListener(View.OnClickListener {
//            Toast.makeText(activity,"Text",Toast.LENGTH_SHORT).show()
//        })


    }

//    fun nowRobo(sbox:ImageView,sbox:ImageView){
//
//    }


    override fun onClick(v: View?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        when(v!!.id){
            R.id.imageView1->
//                selectedItemByPlayer(0)
            gameLogic?.checkTheNextItem(0)

            R.id.imageView2->
//                selectedItemByPlayer(1)
                gameLogic?.checkTheNextItem(1)

            R.id.imageView3->
//                selectedItemByPlayer(2)
                gameLogic?.checkTheNextItem(2)

            R.id.imageView4->
//                selectedItemByPlayer(3)
                gameLogic?.checkTheNextItem(3)

            R.id.imageView5->
//                selectedItemByPlayer(4)
                gameLogic?.checkTheNextItem(4)

            R.id.imageView6->
//                selectedItemByPlayer(5)
                gameLogic?.checkTheNextItem(5)

            R.id.imageView7->
//                selectedItemByPlayer(6)
                gameLogic?.checkTheNextItem(6)

            R.id.imageView8->
//                selectedItemByPlayer(7)
                gameLogic?.checkTheNextItem(7)

            R.id.imageView9->
//                selectedItemByPlayer(8)
                gameLogic?.checkTheNextItem(8)

        }
    }

    fun selectedItemByPlayer(position:Int){

        if (items!![position]!!.isEnabled) {
            items!![position]!!.setImageResource(R.mipmap.cross)
            items!![position]!!.isEnabled = false
            if(atFirst){
                var ranIndex = SecureRandom().nextInt(8)
                if (ranIndex==position){
                    if (ranIndex==8){
                        atFirstCheck(7)
                    }else if (ranIndex==0){
                        atFirstCheck(1)
                    }
                }else{
                    atFirstCheck(ranIndex)
                }
                atFirst = false
                return
            }
            playerX.add(position+1)
            if (!checkForWinner(player)){
//                Toast.makeText(activity,"Now Bot's Turn",Toast.LENGTH_SHORT).show()

                mRunnable  = Runnable {
//                    checkForTheOption(position)
                    botPlayer(position)
                }
                Handler().postDelayed(mRunnable,1000)

            }else{
                Toast.makeText(activity,"You Won!!!",Toast.LENGTH_SHORT).show()
              var  mRunnable1  = Runnable {
                    clearImageView()
                }
                Handler().postDelayed(mRunnable1,1000)
            }

        }
    }

    fun checkForWinner(mPlayer: String):Boolean{
        var winnerList: ArrayList<Int> = arrayListOf()
        winnerList.clear()
        if(mPlayer.equals(player)){
            winnerList.addAll(playerX)
        }else{
            winnerList.addAll(playerO)
        }

        if(getMatched(winnerList,intArrayOf(1,2,3))){
            //Set of Possible X1
            return true
        }else if(getMatched(winnerList,intArrayOf(4,5,6))){
            //Set of Possible X2
            return true
        }else if(getMatched(winnerList,intArrayOf(7,8,9))){
            //Set of Possible X3
            return true
        }else if(getMatched(winnerList,intArrayOf(1,4,7))){
            //Set of Possible Y
            return true
        }else if(getMatched(winnerList,intArrayOf(2,5,8))){
            //Set of Possible Y
            return true
        }else if(getMatched(winnerList,intArrayOf(3,6,9))){
            //Set of Possible Y
            return true
        }else if(getMatched(winnerList,intArrayOf(1,5,9))){
            //Set of Possible XX
            return true
        }else if(getMatched(winnerList,intArrayOf(7,5,3))){
            //Set of Possible YY
            return true
        }


    return false
    }

    fun getMatched(winnerList: ArrayList<Int>,possibility:IntArray): Boolean {

        var countList: ArrayList<Int> = arrayListOf()
        for (possible in possibility){
        for (items in winnerList){
                if (possible == items){
                    countList.add(possible)
                }
            }
        }
        if (countList.size==3) {

            for (i in countList){
                Log.d("Items Matched",i.toString())
            }
            return true
        }

        return false
    }

//    fun arraysMatching(s1: IntArray, s2: IntArray): IntArray {
//
//        val storage = ArrayList()
//        //for (int i = 0; i <= s1.length; i++) {
//        //wrong. change <= to  < or better:
//        for (i in s1) {
//
//            //for (int j = 0; j <= s2.length; j++) {
//            //wrong. change <= to  < or better:
//            for (j in s2) {
//                if (j == i) {
//                    storage.add(j)
//                    break
//                }
//            }
//        }
//
//        //if you can use streams do
//        //return  storage.stream().mapToInt(i->(int)i).toArray();
//
//        //alternatively
//        val returnArray = IntArray(storage.size())
//        for (i in 0 until storage.size()) {
//            returnArray[i] = storage.get(i)
//        }
//        return returnArray
//    }

    fun checkForTheOption(playerPos:Int){

        when(playerPos){
            0-> {
                if (items!![playerPos+1]!!.isEnabled) {
                    items!![playerPos+1]!!.setImageResource(R.mipmap.oimage)
                    items!![playerPos+1]!!.isEnabled = false
                    playerO.add(playerPos+2)

                }else if (!items!![playerPos+3]!!.isEnabled) {
                    items!![playerPos+3]!!.setImageResource(R.mipmap.oimage)
                    items!![playerPos+3]!!.isEnabled = false
                    playerO.add(playerPos+4)

                }else
                    elseCase()
            }
            1 -> {
                if (items!![playerPos+1]!!.isEnabled) {
                    items!![playerPos+1]!!.setImageResource(R.mipmap.oimage)
                    items!![playerPos+1]!!.isEnabled = false
                    playerO.add(playerPos+2)

                }else if (!items!![playerPos+3]!!.isEnabled) {
                    items!![playerPos+3]!!.setImageResource(R.mipmap.oimage)
                    items!![playerPos+3]!!.isEnabled = false
                    playerO.add(playerPos+4)

                }else if (items!![playerPos-1]!!.isEnabled) {
                    items!![playerPos-1]!!.setImageResource(R.mipmap.oimage)
                    items!![playerPos-1]!!.isEnabled = false
                    playerO.add(playerPos)

                }else
                    elseCase()
            }
            2-> elseCase()
            3 -> {if (items!![playerPos+1]!!.isEnabled) {
                items!![playerPos+1]!!.setImageResource(R.mipmap.oimage)
                items!![playerPos+1]!!.isEnabled = false
                playerO.add(playerPos+1)

            }else if (!items!![playerPos+3]!!.isEnabled) {
                items!![playerPos+3]!!.setImageResource(R.mipmap.oimage)
                items!![playerPos+3]!!.isEnabled = false
                playerO.add(playerPos+3)

            }else
                elseCase()
                }
            4-> elseCase()
            5-> elseCase()
            6 -> elseCase()
            7 -> elseCase()
            8 ->elseCase()
            }

        if (checkForWinner("bot")){
            // Log
            Toast.makeText(activity,"Bot Wins ;)",Toast.LENGTH_SHORT).show()
            var  mRunnable1  = Runnable {
                clearImageView()
            }
            Handler().postDelayed(mRunnable1,1000)
        }else{
            Toast.makeText(activity,"Now your Turn",Toast.LENGTH_SHORT).show()
        }


//        var position:Int=0
//
//
//
//        for (imageView in items!!){
//            if (imageView!!.isEnabled){
//
//                imageView!!.setImageResource(R.mipmap.oimage)
//                imageView!!.isEnabled = false
//
//                playerO.add(position+1)
//                if (checkForWinner("bot")){
//                    // Log
//                    Toast.makeText(activity,"Bot Wins ;)",Toast.LENGTH_SHORT).show()
//                    var  mRunnable1  = Runnable {
//                        clearImageView()
//                    }
//                    Handler().postDelayed(mRunnable1,1000)
//                }else{
//                    Toast.makeText(activity,"Now your Turn",Toast.LENGTH_SHORT).show()
//                }
//                return
//
//            }
//            position++
//        }
    }

    fun elseCase(){
        var position:Int=0
        var nOfEnableImageView : Int =0
        for (imageView in items!!){
            if (imageView!!.isEnabled){
                nOfEnableImageView++
                imageView!!.setImageResource(R.mipmap.oimage)
                imageView!!.isEnabled = false

                playerO.add(position+1)
                if (checkForWinner("bot")){
                    // Log
                    Toast.makeText(activity,"Bot Wins ;)",Toast.LENGTH_SHORT).show()
                    var  mRunnable1  = Runnable {
                        clearImageView()
                    }
                    Handler().postDelayed(mRunnable1,1000)
                }else{
                    Toast.makeText(activity,"Now your Turn",Toast.LENGTH_SHORT).show()
                }
                return

            }
            position++
        }
        if (nOfEnableImageView==0){
            clearImageView()
            startButton!!.visibility= View.VISIBLE
            startButton!!.setText("Retry")
            tikTakToeLayout!!.visibility = View.INVISIBLE
        }
    }

    fun clearImageView(){

        for (imageView in items!!){
            imageView!!.isEnabled = true
            imageView!!.setImageResource(android.R.color.transparent)

        }
        playerO.clear()
        playerX.clear()
        atFirst = true
    }

    fun botPlayer(playerPos:Int){

        if(atFirst && playerPos==8){
            items!![playerPos-1]!!.setImageResource(R.mipmap.oimage)
            items!![playerPos-1]!!.isEnabled = false
            playerO.add(playerPos-1)
            atFirst = false
            return
        }

        if (atFirst && playerPos<8){
            if (items!![playerPos+1]!!.isEnabled) {
                items!![playerPos+1]!!.setImageResource(R.mipmap.oimage)
                items!![playerPos+1]!!.isEnabled = false
                playerO.add(playerPos+1)

            }else if(items!![playerPos-1]!!.isEnabled){
                items!![playerPos-1]!!.setImageResource(R.mipmap.oimage)
                items!![playerPos-1]!!.isEnabled = false
                playerO.add(playerPos-1)
            }
            atFirst = false
            return
        }
         if(getPossiblePosition(playerX,intArrayOf(1,2,3))!=0){
            //Set of Possible X1
            checkForWinningFromBot()
            return
        }
        if(getPossiblePosition(playerX,intArrayOf(4,5,6))!=0){
            //Set of Possible X2
            checkForWinningFromBot()
            return
        }
        if(getPossiblePosition(playerX,intArrayOf(7,8,9))!=0){
            //Set of Possible X3
            checkForWinningFromBot()
            return
        }
        if(getPossiblePosition(playerX,intArrayOf(1,4,7))!=0){
            //Set of Possible Y
            checkForWinningFromBot()
            return
        }
        if(getPossiblePosition(playerX,intArrayOf(2,5,8))!=0){
            //Set of Possible Y
            checkForWinningFromBot()
            return
        }
        if(getPossiblePosition(playerX,intArrayOf(3,6,9))!=0){
            //Set of Possible Y
            checkForWinningFromBot()
            return
        }
        if(getPossiblePosition(playerX,intArrayOf(1,5,9))!=0){
            //Set of Possible XX
            checkForWinningFromBot()
            return
        }
        if(getPossiblePosition(playerX,intArrayOf(7,5,3))!=0){
            //Set of Possible YY
            checkForWinningFromBot()
            return
        }else{
            elseCase()
        }

    }

    fun getPossiblePosition(winnerList: ArrayList<Int>,possibility:IntArray): Int {

        var possiblePos :Int =0
        var countList: ArrayList<Int> = arrayListOf()
        for (possible in possibility){
        for (items in winnerList){

                if (possible == items){
                    countList.add(possible)
                }else{
                    possiblePos=possible
                }
            }
        }
        if (countList.size>1) {

            if (items!![possiblePos-1]!!.isEnabled){
                items!![possiblePos-1]!!.setImageResource(R.mipmap.oimage)
                items!![possiblePos-1]!!.isEnabled = false
                playerO.add(possiblePos)
                return possiblePos
            }

        }

        return 0
    }

    fun checkForWinningFromBot(){
        if (checkForWinner("bot")){
            // Log
            Toast.makeText(activity,"Bot Wins ;)",Toast.LENGTH_SHORT).show()
            var  mRunnable1  = Runnable {
                clearImageView()
            }
            Handler().postDelayed(mRunnable1,1000)
        }else{
            Toast.makeText(activity,"Now your Turn",Toast.LENGTH_SHORT).show()
        }
    }

    fun atFirstCheck(botPos:Int){
        items!![botPos]!!.setImageResource(R.mipmap.oimage)
        items!![botPos]!!.isEnabled = false
        playerO.add(botPos)
        atFirst = false
        Toast.makeText(activity,"Now your Turn",Toast.LENGTH_SHORT).show()
    }

    override fun updateTheUI(player: String, position: Int) {
        if (player.equals(BPlayer)){
            items!![position]!!.setImageResource(R.mipmap.oimage)
            items!![position]!!.isEnabled = false
        } else if (player.equals(CPlayer)){
            items!![position]!!.setImageResource(R.mipmap.cross)
            items!![position]!!.isEnabled = false
        }
    }

    override fun clearTheImageView() {
        for (imageView in items!!){
            imageView!!.isEnabled = true
            imageView!!.setImageResource(android.R.color.transparent)

        }
    }
}