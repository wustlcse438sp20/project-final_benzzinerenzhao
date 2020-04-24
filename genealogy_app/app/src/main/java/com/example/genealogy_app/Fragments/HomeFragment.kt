package com.example.genealogy_app.Fragments

import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.genealogy_app.Activities.PersonalInfoActivity
import com.example.genealogy_app.DataClasses.*
import com.example.genealogy_app.FamilyTree
import com.example.genealogy_app.R
import com.example.genealogy_app.ViewModel.HomeViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : Fragment(){

    val TAG = "HomeFragment.kt"

    //used for scrolling
    var downX = 0f
    var downY = 0f
    var xScrolled = 0f
    var yScrolled = 0f
    private lateinit var viewModel: HomeViewModel

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
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        //disable this once we have actual trees created


        val arguments = arguments // this is supposed to have the id, but its still null
        if(arguments != null && arguments.containsKey("treeId")){

            val treeId = arguments.getString("treeId")

            val db = Firebase.firestore
            db.collection("trees").document(treeId!!).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "Successfully got documentSnapshot")

                        val tree = document.toObject<Tree>()
                        //Toast.makeText(activity, tree.toString(), Toast.LENGTH_LONG).show()

                        // uncomment these to print tree that user clicked on after fixing above code
                        var root = Member(1)
                        val person = tree!!.ancestor!!.person
                        root.person = person!!
                        viewModel.currentTree = FamilyTree(root)
                        tree_view.setImageDrawable(viewModel.currentTree)

                    } else {
                        Log.d(TAG, "Successfully queried collection, but document was null")
                    }
                }
        } else{
            createDebugTree()
        }


        tree_view.setLongClickable(true)
        //set scrolling ontouch listener
        tree_view.setOnTouchListener(){view: View, motion: MotionEvent ->
            treeTouchListener(view, motion)
        }

        tree_view.setOnLongClickListener(){view: View ->
            treeLongClickListener(view)
        }
        tree_view.x=0f
        tree_view.x=0f


    }

    //creates an example tree view. useful if user has no data
    fun createDebugTree() {
        val p1 = Person(/*id= UUID.randomUUID(),*/givenName = "Donal]d",surname = "Trump")
        val p2= Person(/*id= UUID.randomUUID(),*/givenName = "Melania",surname = "Trump")
        val p3 = Person(/*id= UUID.randomUUID(),*/givenName = "Ivanka",surname = "Trump")
        val p4= Person(/*id= UUID.randomUUID(),*/givenName = "Donald Jr.",surname = "Trump")
        val p5 = Person(/*id= UUID.randomUUID(),*/givenName = "Jared",surname = "Kushner")
        val donald:Member = Member(1)
        val melania:Spouse = Spouse(1)
        val ivanka:Member = Member(1)
        val donaldjr:Member = Member(2)
        val jared:Spouse = Spouse(1)
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
        viewModel.currentTree=FamilyTree(donald)
        tree_view.setImageDrawable(viewModel.currentTree)



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
                xScrolled+=(downX-currentX).toInt()
                yScrolled+=(downY-currentY).toInt()
                downX = currentX
                downY = currentY
                return true
            }
        }

        return false
    }

    fun treeLongClickListener(view:View):Boolean{
        var x = downX+xScrolled
        var y =downY+yScrolled
        val pointTapped = PointF(x,y)
        var tappedMember= locateTapped(viewModel.currentTree!!.mAncestor,pointTapped)
        if (tappedMember!=null){
            var tappedPerson = tappedMember!!.person
            val personalInfoIntent = Intent(this.context,PersonalInfoActivity::class.java)
            personalInfoIntent.putExtra("firstName",tappedPerson.givenName)
            personalInfoIntent.putExtra("lastName",tappedPerson.surname)
            //personalInfoIntent.putExtra("id", tappedPerson.id)
            //personalInfoIntent.putExtra("gender",genderToString(tappedPerson.gender))
            var DOB="unknown"
            if(tappedPerson.birthDate!=null){
                DOB=tappedPerson.birthDate.toString()
            }
            personalInfoIntent.putExtra("DOB",DOB)
            var birthPlace="unknown"
            if(tappedPerson.birthPlace!=null){
                var temp = tappedPerson.birthPlace
                birthPlace=temp!!
            }
            personalInfoIntent.putExtra("birthPlace",birthPlace)
            var biography="unknown"
            if(tappedPerson.biography!=null){
                var temp = tappedPerson.biography
                biography=temp!!
            }
            personalInfoIntent.putExtra("biography",biography)
            startActivity(personalInfoIntent)
        }

        return true
    }

    private fun genderToString(gender: Gender?):String{
        if (gender==Gender.male){
            return "male"
        }
        if (gender==Gender.female){
            return "female"
        }
        if (gender==Gender.other){
            return "other"
        }
        return "unknown"
    }
    private fun locateTapped(member: Member, tapped: PointF): Membership? {
        if (tapped.x >= member.x && tapped.x < member.x + member.width && tapped.y >= member.y &&
            (tapped.y <= member.y + member.height || !viewModel.currentTree!!.isSingle(member) && tapped.y <= member.y + member.height + viewModel.currentTree!!.signSize * 4)
        ) {
            return member
        }
        if (!viewModel.currentTree!!.isSingle(member)) {
            val spouses = member.spouses
            if (spouses != null) {
                for (spouse in spouses) {
                    if (tapped.x >= spouse.x && tapped.x < spouse.x + member.width && tapped.y >= spouse.y && tapped.y < spouse.y + spouse.height) {
                        return spouse
                    }
                }
            }
            val children = member.children
            if (children != null) {
                for (child in children) {
                    val ret: Membership? = locateTapped(child, tapped)
                    if (ret != null) {
                        return ret
                    }
                }
            }
        }
        return null
    }


}
