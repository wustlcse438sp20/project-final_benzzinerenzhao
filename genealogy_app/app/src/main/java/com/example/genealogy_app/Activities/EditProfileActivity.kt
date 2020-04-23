package com.example.genealogy_app.Activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.genealogy_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_register.*

class EditProfileActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
    }
    var auth = FirebaseAuth.getInstance()
    var db = Firebase.firestore
    var email = auth.currentUser!!.email

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        /*auth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        email = auth.currentUser!!.email*/


        //db.collection("users") is indexed by email as the key, so you can query the users collection with auth.currentUser!!.email
        //  to get the current user's document
    }
    fun submitProfileEdits(view: View){
        if(edit_first_name_field_profile!=null){
            var newFirstName = edit_first_name_field_profile.text.toString()
            /*db.collection("user").document(email!!)
                .update("trees", )*/
        }
        if(edit_last_name_field_profile!=null){
            var newLastName = edit_last_name_field_profile.text.toString()
        }
        if(edit_dob_field_profile!=null){
            var newDOB = edit_dob_field_profile.text.toString()
        }
        if(edit_location_field_profile!=null){
            var newLocation = edit_location_field_profile.text.toString()
        }
        if(edit_bio_field_profile!=null){
            var newBiography = edit_bio_field_profile.text.toString()
        }

    }
}