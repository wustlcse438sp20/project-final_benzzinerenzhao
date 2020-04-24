package com.example.genealogy_app.Activities

import android.app.Activity
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
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream


class EditPersonActivity : AppCompatActivity() {


    override fun onStart() {
        super.onStart()
    }
    var auth = FirebaseAuth.getInstance()
    var db = Firebase.firestore
    var email = auth.currentUser!!.email
    val TAG = "EditPersonActivity.kt"

    val PICK_PROFILE_IMAGE = 1

    var imageBase64 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_person)




        //db.collection("users") is indexed by email as the key, so you can query the users collection with auth.currentUser!!.email
        //  to get the current user's document

    }

    fun submitEdits(view: View){


        //store updates in this hashmap
        var update = HashMap<String, Any>()

        if (imageBase64 != "") {
            update.put("image", imageBase64)
        }

        if(edit_first_name_field!=null){
            var newFirstName = edit_first_name_field.text.toString()
            /*db.collection("user").document(email!!)
                .update("trees", )*/
            update.put("givenName", newFirstName)
        }
        if(edit_last_name_field!=null){
            var newLastName = edit_last_name_field.text.toString()
            update.put("surname", newLastName)
        }
        if(edit_dob_field!=null){
            var newDOB = edit_dob_field.text.toString()
            update.put("birthDate", newDOB)
        }
        if(edit_location_field!=null){
            var newLocation = edit_location_field.text.toString()
            update.put("birthPlace", newLocation)
        }
        if(edit_bio_field!=null){
            var newBiography = edit_bio_field.text.toString()
            update.put("biography", newBiography)
        }

        //TODO: update person. Need to grab UUID from PersonalInfoActivity
        //var document = db.collection("collection").document()

    }

    fun addChild(view: View){

    }
    fun addSpouse(view:View){

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
                edit_picture_person.setImageBitmap(imageBitmap)

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