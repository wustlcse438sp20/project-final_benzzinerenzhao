package com.example.genealogy_app.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.genealogy_app.Adapters.TabAdapter
import com.example.genealogy_app.Fragments.HomeFragment
import com.example.genealogy_app.R
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

        val intent = intent
        val extras = intent.extras
        var treeId:String?
        if(extras != null){
            if(extras.containsKey("treeId")){
                treeId = extras.getString("treeId")
                Toast.makeText(this, treeId, Toast.LENGTH_LONG).show()
                val bundle = Bundle()
                bundle.putString("treeId", treeId)
                val fragInfo = HomeFragment()
                fragInfo.setArguments(bundle)
                val transaction = this.supportFragmentManager.beginTransaction()
                transaction.commit()
            }
        }



        //db.collection("users") is indexed by email as the key, so you can query the users collection with auth.currentUser!!.email
        //  to get the current user's document
    }
}
