package com.example.genealogy_app.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.genealogy_app.Activities.LoginActivity

import com.example.genealogy_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    val TAG: String = "ProfileFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var auth = FirebaseAuth.getInstance()
        var email = auth.currentUser!!.email

        var db = FirebaseFirestore.getInstance()
        db.collection("users").document(email!!).get()
            .addOnSuccessListener {document ->
                if (document != null) {
                    Log.d(TAG, "Successfully got documentSnapshot")
                    updateProfileView(document.data!!)
                }
                else {
                    Log.d(TAG, "Successfully queried collection, but document was null")
                }

            }
            .addOnFailureListener {
                Log.d(TAG,"Failed to get documentSnapshot")
            }

        //handle logout click
        profile_logout_button.setOnClickListener() {view ->
            Log.d(TAG, "clicked logout button")
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity!!.finish()
        }



    }

    fun updateProfileView(data: Map<String, Any>) {
        var name = data["firstName"].toString() + " " + data["lastName"].toString()
        profile_name_text.text = name;

        var dob = data["dob"]
        if (dob != null && dob.toString() != "")
            profile_dob.text = "DOB: " + dob.toString()

        var location = data["location"]
        if (location != null && location.toString() != "")
            profile_location.text = "Location: " + location.toString()

        var bio = data["bio"]
        if (bio != null && bio.toString() != "")
            profile_bio.text = bio.toString()
    }



}
