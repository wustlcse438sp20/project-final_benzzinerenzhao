package com.example.genealogy_app.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.genealogy_app.DataClasses.TreeListItem
import com.example.genealogy_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val TAG: String = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore
    }

    //called by submit button on register activity
    fun handleSubmit(view: View?) {
        //TODO: we need to either hash passwords or make it very clear to the user that the passwords are stored as plain text
        if (checkForm()) {
            //create new user in firebase auth
            val email = email_field.text.toString()
            val password = register_password_field.text.toString()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Successfully added user to FirebaseAuth with email: " + email)

                        //now add to users table in firebase database
                        val user = hashMapOf(
                            "email" to email,
                            "password" to password,
                            "firstName" to first_name_field.text.toString(),
                            "middleName" to middle_name_field.text.toString(),
                            "lastName" to last_name_field.text.toString(),
                            "dob" to dob_field.text.toString(),
                            "location" to location_field.text.toString(),
                            "bio" to bio_field.text.toString(),
                            "trees" to ArrayList<TreeListItem>()
                        )

                        db.collection("users").document(email).set(user)
                            .addOnSuccessListener {
                                Log.d(TAG, "DocumentSnapshot successfully added")
                                val toast = Toast.makeText(applicationContext, "Account successfully created", Toast.LENGTH_SHORT)
                                toast.show()
                                finish()
                            }
                            .addOnFailureListener {
                                Log.d(TAG, "Failed to add DocumentSnapshot")
                                val toast = Toast.makeText(applicationContext, "Error: Failed to create account", Toast.LENGTH_SHORT)
                                toast.show()
                            }

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
