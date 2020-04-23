package com.example.genealogy_app.Adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.genealogy_app.Activities.TreeActivity
import com.example.genealogy_app.DataClasses.TreeListItem
import com.example.genealogy_app.Fragments.HomeFragment
import com.example.genealogy_app.R

class TreeListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_treelist, parent, false)) {

    val nameText: TextView
    var name: String
    var id: String

    init {
        nameText = itemView.findViewById(R.id.item_treeName)
        name = "TreeListViewHolder has not yet been binded!"
        id = "TreeListViewHolder has not yet been binded!"
    }

    fun bind (tree: TreeListItem) {
        name = tree.name
        id = tree.id
        nameText.text = tree.name
        itemView.setOnClickListener{
            val treeIntent = Intent(itemView.context, TreeActivity::class.java)
            treeIntent.putExtra("treeId", id)
            ContextCompat.startActivity(itemView.context, treeIntent, null)
        }
    }

}


class TreeListAdapter(val list: ArrayList<TreeListItem>?) : RecyclerView.Adapter<TreeListViewHolder>() {
    private var treeList = list
    val TAG = "TreeListAdapter.kt"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreeListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val holder = TreeListViewHolder(inflater, parent)

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d(TAG, "clicked on TreeListItem with id = '" + holder.id + "' and name = '" + holder.name + "'")

            }
        })

        return holder
    }

    override fun onBindViewHolder(holder: TreeListViewHolder, position: Int) {
        val tree: TreeListItem = treeList!!.get(position)
        holder.bind(tree)
    }

    override fun getItemCount(): Int {
        return treeList!!.size
    }

}