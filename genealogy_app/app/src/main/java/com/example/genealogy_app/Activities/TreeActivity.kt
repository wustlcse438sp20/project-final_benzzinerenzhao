package com.example.genealogy_app.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.genealogy_app.Adapters.TabAdapter
import com.example.genealogy_app.R
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_tree.*

//this is the main activity, has three tabs at the bottom
class TreeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree)

        var tabLayoutAdapter = TabAdapter(supportFragmentManager)
        viewPager.adapter = tabLayoutAdapter


        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore


        //db.collection("users") is indexed by email as the key, so you can query the users collection with auth.currentUser!!.email
        //  to get the current user's document
    }
}
