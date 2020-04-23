package com.example.genealogy_app.Activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.genealogy_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_edit_person.*
import android.content.Intent






class EditPersonActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
    }
    var auth = FirebaseAuth.getInstance()
    var db = Firebase.firestore
    var email = auth.currentUser!!.email

    val PICK_PROFILE_IMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_person)




        //db.collection("users") is indexed by email as the key, so you can query the users collection with auth.currentUser!!.email
        //  to get the current user's document

    }
    fun submitEdits(view: View){
        if(edit_first_name_field!=null){
            var newFirstName = edit_first_name_field.text.toString()
            /*db.collection("user").document(email!!)
                .update("trees", )*/
        }
        if(edit_last_name_field!=null){
            var newLastName = edit_last_name_field.text.toString()
        }
        if(edit_dob_field!=null){
            var newDOB = edit_dob_field.text.toString()
        }
        if(edit_location_field!=null){
            var newLocation = edit_location_field.text.toString()
        }
        if(edit_bio_field!=null){
            var newBiography = edit_bio_field.text.toString()
        }

    }

    //called by the imageView to change a profile picture, starts an intent to gallery to pick an image
    fun editImageOnClick(view: View?) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PROFILE_IMAGE)
    }

    //called when user is done picking an image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == PICK_PROFILE_IMAGE) {
            if (data == null) {
                //error or (?) user did not pick an image
                return
            } else {
                val inputStream = getContentResolver().openInputStream(data.data!!)
                
            }
        }
    }
}