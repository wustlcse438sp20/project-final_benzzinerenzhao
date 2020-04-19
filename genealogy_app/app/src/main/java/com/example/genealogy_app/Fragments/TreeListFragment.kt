package com.example.genealogy_app.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.genealogy_app.Adapters.TreeListAdapter
import com.example.genealogy_app.DataClasses.TreeListItem

import com.example.genealogy_app.R
import kotlinx.android.synthetic.main.fragment_tree_list.*
import androidx.recyclerview.widget.DividerItemDecoration




class TreeListFragment : Fragment() {

    var list = ArrayList<TreeListItem>()
    private lateinit var adapter: TreeListAdapter

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


}
