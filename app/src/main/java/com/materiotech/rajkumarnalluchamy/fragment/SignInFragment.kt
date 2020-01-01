package com.materiotech.rajkumarnalluchamy.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.materiotech.rajkumarnalluchamy.R
import com.materiotech.rajkumarnalluchamy.model.User
import java.util.regex.Pattern
import android.content.Intent
import android.content.SharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import com.materiotech.rajkumarnalluchamy.activity.HomeActivity


class SignInFragment : Fragment(){

    var userName:EditText?=null
    var emailId:EditText?=null
    var signInGmail:SignInButton?=null
    private lateinit var database: DatabaseReference
    val PICK_CONTACT_REQUEST = 1

    var emailPattern:String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "myAccount"

    companion object {
        private const val REQUEST_INVITE = 101
        fun newInstance(): SignInFragment =
            SignInFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()

        // Build a GoogleSignInClient with the options specified by gso.
//        googleSignInClient = GoogleSignIn.getClient(activity?.applicationContext, gso);
        // [START initialize_auth]
        // Initialize Firebase Auth
//        auth = FirebaseAuth.getInstance()
        // [END initialize_auth]



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater?.inflate(
            R.layout.fragment_signin,
            container, false)
        getWidget(rootView)
        return rootView
    }

    private fun getWidget(rootView: View){
        userName = rootView.findViewById(R.id.useIdEditTextView)
        emailId = rootView.findViewById(R.id.emailIdEditTextView)
//        signInGmail = rootView.findViewById(R.id.signInView)

        emailId?.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    connectToDB()
                    true
                }
                else -> false
            }
        }

        signInGmail?.setOnClickListener(View.OnClickListener {
            signIn();

        })

    }

    fun connectToDB(){
        var lValidation: Boolean = false

        if (userName?.text.toString()!=null && userName?.text.toString().length>2)
            lValidation= true
        else {
            Toast.makeText(activity?.applicationContext, "USERNAME LENGTH MIN 2 CHARACTERS ", Toast.LENGTH_SHORT).show()
            userName?.requestFocus()
            return
        }

        if (emailId?.text.toString()!=null && emailId?.text.toString().length>0 && isValidEmailId(emailId?.text.toString()))
            lValidation= true
        else{
            Toast.makeText(activity?.applicationContext, "PLEASE ENTER VALID EMAIL ID", Toast.LENGTH_SHORT).show()
            emailId?.requestFocus()
            return
        }

        if (lValidation){
            writeNewUser(userName?.text.toString(),emailId?.text.toString())
        }


    }

    private fun isValidEmailId(email:String):Boolean {

    return Pattern.compile(
         "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
}

    private fun writeNewUser(userId: String,  email: String) {
        val user = User(userId, email.replace(".",","))
//        if(database.child("users").child(userId).)
//        database.child("users").child(userId).setValue(user)
//            .addOnSuccessListener {
//                // Write was successful!
//                // ...
////                var myData = Gson()
////                val sharedPref: SharedPreferences = activity!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
////                val editor = sharedPref.edit()
////                editor.putBoolean(PREF_NAME, true)
////                editor.putString("Materio_Users",myData.toJson(user))
////                editor.apply()
//
//
//
//
//            }
//            .addOnFailureListener {
//                // Write failed
//                // ...
//            }

        database.child("users").child(email.replace(".",",")).addListenerForSingleValueEvent(object : ValueEventListener {



            override fun onCancelled(p0: DatabaseError) {
                Log.d("DatabaseError",p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    Log.d("FIREDATA_BASE","YES")
                }else{
                    Log.d("FIREDATA_BASE","NO")
                    database.child("users").child(userId).setValue(user)
            .addOnSuccessListener {
                // Write was successful!
                // ...
                var myData = Gson()
                val sharedPref: SharedPreferences = activity!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
                val editor = sharedPref.edit()
                editor.putBoolean(PREF_NAME, true)
                editor.putString("Materio_Users",myData.toJson(user))
                editor.apply()
                val intent = Intent(activity, HomeActivity::class.java)
                startActivityForResult(intent,PICK_CONTACT_REQUEST)


            }
            .addOnFailureListener {
                // Write failed
                // ...
                Toast.makeText(activity,"Please Enter The Valid Credential", Toast.LENGTH_SHORT).show()
            }
                }
               }

//
        })
    }


    private fun signIn() {
//        val signInIntent = mGoogleSignInClient.getSignInIntent()
//        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

}