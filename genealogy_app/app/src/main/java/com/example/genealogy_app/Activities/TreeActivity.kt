package com.example.genealogy_app.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.genealogy_app.Fragments.HomeFragment
import com.example.genealogy_app.Fragments.ProfileFragment
import com.example.genealogy_app.Fragments.TreeListFragment
import com.example.genealogy_app.R
import com.example.genealogy_app.ViewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_tree.*




//this is the main activity, has three tabs at the bottom
class TreeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var treeId:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree)


        var isTree = false
        val intent = intent
        val extras = intent.extras
        if(extras != null){
            if(extras.containsKey("treeId")){
                treeId = extras.getString("treeId")
                //Toast.makeText(this, treeId, Toast.LENGTH_LONG).show()
                isTree = true
            }
        }

        class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

            override fun getCount(): Int  = 3

            override fun getItem(i: Int): Fragment {
                var fragment: Fragment

                when(i) {
                    0 -> {
                            fragment = HomeFragment()
                            if(isTree){

                                val bundle = Bundle()
                                bundle.putString("treeId", treeId)
                                fragment.setArguments(bundle)
                            }
                        }
                    1 -> fragment = TreeListFragment()
                    2 -> fragment = ProfileFragment()
                    else -> fragment = HomeFragment()
                }

                return fragment
            }

            override fun getPageTitle(position: Int): CharSequence {
                when(position) {
                    0 -> return "Home"
                    1 -> return "Trees"
                    2 -> return "Profile"
                    else -> return "error"
                }
            }
        }

        var tabLayoutAdapter = TabAdapter(supportFragmentManager)
        viewPager.adapter = tabLayoutAdapter

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore

        //db.collection("users") is indexed by email as the key, so you can query the users collection with auth.currentUser!!.email
        //  to get the current user's document
    }
}
