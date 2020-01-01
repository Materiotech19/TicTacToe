package com.materiotech.rajkumarnalluchamy.activity

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.materiotech.rajkumarnalluchamy.R
import com.materiotech.rajkumarnalluchamy.fragment.InviteFriendFragment
import com.materiotech.rajkumarnalluchamy.fragment.SignInFragment
import com.materiotech.rajkumarnalluchamy.fragment.TicTakToeFragment
import com.materiotech.rajkumarnalluchamy.model.User

class HomeActivity : AppCompatActivity() {

//    var recyclerView : RecyclerView?=null;
    var layoutManager: LinearLayoutManager? =null
    private lateinit var database: DatabaseReference
    lateinit var toolbar: ActionBar
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "myAccount"
    val PICK_CONTACT_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        var gson = Gson()

        val user = gson.fromJson(sharedPref.getString("Materio_Users",""), User::class.java)

//        setContentView(R.layout.layout_botton_bar)
        if (user==null){
            setContentView(R.layout.fragment_layout)
            openSignInFragment(SignInFragment.newInstance())
            return
        }else{
            setContentView(R.layout.layout_botton_bar)
        }

//        initWidget()

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigation.selectedItemId = R.id.action_game
    }

    fun  initWidget(){

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_layout, SignInFragment(), "rageComicList")
            .commit()

    }

    public fun callTheFragment(fragmentName : String){
        when(fragmentName){

        }

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_share -> {
                toolbar.title = "Share With Friends"
                val shareFragment = InviteFriendFragment.newInstance()
                openFragment(shareFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_game -> {
                toolbar.title = "Play The Game"
                val gameFragment = TicTakToeFragment.newInstance()
                openFragment(gameFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_account -> {
                toolbar.title = "My Account"
                val accountFragment = TicTakToeFragment.newInstance()
                openFragment(accountFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun openSignInFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == PICK_CONTACT_REQUEST){
            Log.d("HomeActivity","SignIn")

            toolbar.title = "Play The Game"
            val gameFragment = TicTakToeFragment.newInstance()
            openFragment(gameFragment)
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
    }
}
