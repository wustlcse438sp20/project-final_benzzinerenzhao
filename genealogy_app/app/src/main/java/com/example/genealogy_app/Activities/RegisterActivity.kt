package com.example.genealogy_app.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.genealogy_app.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.email_field
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG: String = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
    }

    //called by submit button on register activity
    fun handleSubmit(view: View?) {
        if (checkForm()) {
            //create new user in firebase auth
            val email = email_field.text.toString()
            val password = register_password_field.text.toString()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Successfully added user to FirebaseAuth with email: " + email)
                    } else {
                        Log.d(TAG, "Error: failed to add user to FirebaseAuth with email: " + email)
                    }
                }
        } else {
            Log.d(TAG, "Error: checkForm() returned false")
        }
    }

    //checks register form inputs; returns true if all fields are valid, false otherwise
    fun checkForm(): Boolean {
        var isValid = true
        Log.d(TAG, "Checking form inputs")
        val email = email_field.text.toString()
        if (email == "") {
            isValid = false
            email_field.error = "required"
        }

        val firstName = first_name_field.text.toString()
        if (firstName == "") {
            isValid = false
            first_name_field.error = "required"
        }

        val lastName = last_name_field.text.toString()
        if (lastName == "") {
            isValid = false
            last_name_field.error = "required"
        }

        val dob = dob_field.text.toString()
        Log.d(TAG, "dob: " + dob)
        if (dob == "") {
            //TODO: need to check that this is actually a valid date, currently just checks that there is any input at all
            isValid = false
            dob_field.error = "required"
        }

        val password = register_password_field.text.toString()
        if (password == "" || password.length < 6) {
            isValid = false;
            register_password_field.error = "Must be at least 6 characters long"
        }

        return isValid
    }
}
