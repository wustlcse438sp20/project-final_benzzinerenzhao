package com.example.genealogy_app.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.genealogy_app.DataClasses.Member
import com.example.genealogy_app.DataClasses.Person
import com.example.genealogy_app.DataClasses.Spouse
import com.example.genealogy_app.FamilyTree

import com.example.genealogy_app.R
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : Fragment() {

    val TAG = "HomeFragment.kt"

    //used for scrolling
    var downX = 0f
    var downY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()

        //disable this once we have actual trees created
        createDebugTree()

        //set scrolling ontouch listener
        tree_view.setOnTouchListener(){view: View, motion: MotionEvent ->
            treeTouchListener(view, motion)
        }


    }

    //creates an example tree view. useful if user has no data
    fun createDebugTree() {
        var p1 = Person(id= UUID.randomUUID(),givenName = "Donald",surname = "Trump")
        var p2= Person(id= UUID.randomUUID(),givenName = "Melania",surname = "Trump")
        var p3 = Person(id= UUID.randomUUID(),givenName = "Ivanka",surname = "Trump")
        var p4= Person(id= UUID.randomUUID(),givenName = "Donald Jr.",surname = "Trump")
        var p5 = Person(id= UUID.randomUUID(),givenName = "Jared",surname = "Kushner")
        var donald:Member = Member(1)
        var melania:Spouse = Spouse(1)
        var ivanka:Member = Member(1)
        var donaldjr:Member = Member(2)
        var jared:Spouse = Spouse(1)
        donald.person=p1
        melania.person=p2
        ivanka.person = p3
        donaldjr.person=p4
        jared.person = p5
        donald.children=ArrayList<Member>()
        donald.children!!.add(ivanka)
        donald.children!!.add(donaldjr)
        ivanka.spouses = ArrayList<Spouse>()
        donald.spouses = ArrayList<Spouse>()
        ivanka.spouses!!.add(jared)
        donald.spouses!!.add(melania)
        ivanka.father=donald
        ivanka.mother=melania
        donaldjr.father=donald
        donaldjr.mother=melania

        tree_view.setImageDrawable(FamilyTree(donald))
    }

    //touch listener for scrolling on the imageview of the tree
    //citation: https://stackoverflow.com/questions/3058164/android-scrolling-an-imageview -- the java code
    //  in the answers of this stack overflow question helped me develop this function
    fun treeTouchListener(view: View, motion: MotionEvent): Boolean {
        //Log.d(TAG, "detected touch on tree view")

        var currentX: Float = 0f
        var currentY: Float = 0f

        when (motion.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = motion.x
                downY = motion.y
            }
            MotionEvent.ACTION_MOVE -> {
                currentX = motion.x
                currentY = motion.y
                tree_view.scrollBy((downX - currentX).toInt(), (downY - currentY).toInt())
                downX = currentX
                downY = currentY
            }
        }

        return true
    }


}
