package com.example.genealogy_app.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.genealogy_app.Adapters.TabAdapter
import com.example.genealogy_app.R
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_tree.*

//this is the main activity, has three tabs at the bottom
class TreeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree)

        var tabLayoutAdapter = TabAdapter(supportFragmentManager)
        viewPager.adapter = tabLayoutAdapter

    }
}
