package com.example.genealogy_app.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.genealogy_app.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

//this is the login activity
class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val TAG: String = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        //currentUser will be null if there is no user
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.d(TAG, "no user login detected, displaying login page")
            //do nothing -- current activity is a login/signup page
        } else {
            Log.d(TAG, "user login detected, moving to main")
            //start intent to treeActivity
            val intent = Intent(this, TreeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //called by clicking on login button
    fun loginHandler(view: View?) {
        val email = login_email_field.text.toString()
        val password = login_password_field.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Toast.makeText(baseContext, "Login success!",
                Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TreeActivity::class.java)
                startActivity(intent)
                //call finish here so that hitting the back button results in exiting the app, not returning to the login screen
                finish()
            }
            .addOnFailureListener{
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
    }

    //called by onClick on register button
    fun registerHandler(view: View?) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
