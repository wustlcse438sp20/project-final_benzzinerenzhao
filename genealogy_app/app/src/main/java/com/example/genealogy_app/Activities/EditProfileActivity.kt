package com.example.genealogy_app.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.genealogy_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import java.io.ByteArrayOutputStream

class EditProfileActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()

    }
    var auth = FirebaseAuth.getInstance()
    var db = Firebase.firestore
    var email = auth.currentUser!!.email

    val PICK_PROFILE_IMAGE = 1
    val TAG = "EditProfileActivity.kt"

    var imageBase64 = ""

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
        var result = Intent()
        if(edit_first_name_field_profile!=null && edit_first_name_field_profile.text.toString() != ""){
            var newFirstName = edit_first_name_field_profile.text.toString()
            db.collection("users").document(email!!)
                .update("firstName",newFirstName)
        }
        if(edit_last_name_field_profile != null && edit_last_name_field_profile.text.toString() != ""){
            var newLastName = edit_last_name_field_profile.text.toString()
            db.collection("users").document(email!!)
                .update("lastName",newLastName)
        }
        if(edit_dob_field_profile.text!=null&&edit_dob_field_profile.text.toString()!=""){
            var newDOB = edit_dob_field_profile.text.toString()
            db.collection("users").document(email!!)
                .update("dob",newDOB)
        }
        if(edit_location_field_profile.text!=null&&edit_location_field_profile.text.toString()!=""){
            var newLocation = edit_location_field_profile.text.toString()
            db.collection("users").document(email!!)
                .update("location",newLocation)
        }
        if(edit_bio_field_profile.text!=null&&edit_bio_field_profile.text.toString()!=""){
            var newBiography = edit_bio_field_profile.text.toString()
            db.collection("users").document(email!!)
                .update("bio",newBiography)
        }

        if (imageBase64 != "") {
            db.collection("users").document(email!!)
                .set(hashMapOf("image" to imageBase64), SetOptions.merge())
        }

        setResult(0,result)
        this.finish()
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

        if (requestCode == PICK_PROFILE_IMAGE && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "returned from picking image")
            if (data == null) {
                //error or (?) user did not pick an image
                return
            } else {
                val inputStream = getContentResolver().openInputStream(data.data!!)

                //change the picture on the form
                val imageBitmap = BitmapFactory.decodeStream(inputStream)
                edit_picture_profile.setImageBitmap(imageBitmap)

                //limit filesize of image (firestore has a max size of document = 1mB
                var stream = ByteArrayOutputStream()
                var quality = 100
                var size = 0
                var maxByteSize = 100000 //limits filesize to 100000 bytes or about 10kb
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)

                //keep compressing until under required file size
                while (stream.toByteArray().size > maxByteSize) {
                    quality = quality - 5
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
                }

                //create Base64 string to store in firebase
                imageBase64 = Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP)
                Log.d(TAG, "image base64: " + imageBase64)
            }
        }
    }
}