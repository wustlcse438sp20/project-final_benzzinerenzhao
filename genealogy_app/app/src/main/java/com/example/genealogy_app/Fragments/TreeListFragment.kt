package com.example.genealogy_app.Fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.genealogy_app.Adapters.TreeListAdapter
import com.example.genealogy_app.DataClasses.TreeListItem

import com.example.genealogy_app.R
import kotlinx.android.synthetic.main.fragment_tree_list.*
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog_add_tree.*


class TreeListFragment : Fragment() {

    var list = ArrayList<TreeListItem>()
    private lateinit var adapter: TreeListAdapter

    private lateinit var db: FirebaseFirestore

    val TAG = "TreeListFragment.kt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_tree_list, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: this adds debug data to demonstrate the functionality of the recyclerview and adapter. remove this when we have data in the 'trees' field in a users document
        list.add(TreeListItem("test name 1", "testid1"))
        list.add(TreeListItem("test name 2", "testid2"))
        list.add(TreeListItem("test name 3", "testid3"))
        adapter = TreeListAdapter(list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context!!,
                DividerItemDecoration.VERTICAL
            )
        )

    }

    override fun onStart() {
        super.onStart()

        var auth = FirebaseAuth.getInstance()
        var email = auth.currentUser!!.email
        db = Firebase.firestore

        //Get user trees (ArrayList<TreeListItem>) stored in collection users -> document -> trees
        db.collection("users").document(email!!).get()
            .addOnSuccessListener {document ->
                if (document != null) {
                    Log.d(TAG, "Successfully got documentSnapshot")
                    list = document.data!!["trees"] as ArrayList<TreeListItem>
                }
                else {
                    Log.d(TAG, "Successfully queried collection, but document was null")
                }

            }
            .addOnFailureListener {
                Log.d(TAG,"Failed to get documentSnapshot")
            }


        addFloatingActionButton()
    }

    //create the dialog for the FAB, add the FAB to view
    fun addFloatingActionButton() {
        //Set floating action button stuff for creating a new tree
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_add_tree, null)
        val builder = AlertDialog.Builder(context!!)
        val dialog: AlertDialog? = activity?.let {
            builder.setView(view)
                // Add action buttons
                .setPositiveButton("Create Tree",
                    DialogInterface.OnClickListener { dialog, id ->
                        val name = treeName_field.text.toString()
                        if (name != null && name.length > 3) {
                            addTree(name)
                        }
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        //user canceled dialog
                        dialog.cancel()
                    })
                .setTitle("Create New Family Tree")
            builder.create()
        }

        //set on click listener for add playlist button
        val fab = fab
        fab.setOnClickListener { view ->
            dialog!!.show()
        }
    }


    fun addTree(name: String) {
        //TODO: add tree to tree collection, and then get the ID of that document and store the name and ID as a TreeListItem in the trees field of the user collection
    }


}
