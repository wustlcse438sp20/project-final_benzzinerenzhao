package com.example.genealogy_app.Activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.genealogy_app.Adapters.TabAdapter
import com.example.genealogy_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_tree.*

class EditPersonActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_person)

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore


        //db.collection("users") is indexed by email as the key, so you can query the users collection with auth.currentUser!!.email
        //  to get the current user's document
    }
    fun submitEdits(view: View){
        if(first_name_field!=null){
            var newFirstName = first_name_field.text.toString()
            /*db.collection("players").document(viewModel.email!!)
                .update("chips", viewModel.player.chips)*/
        }
        if(last_name_field!=null){
            var newLastName = last_name_field.text.toString()
        }
        if(dob_field!=null){
            var newDOB = dob_field.text.toString()
        }
        if(location_field!=null){
            var newLocation = location_field.text.toString()
        }
        if(bio_field!=null){
            var newBiography = bio_field.text.toString()
        }

    }
}