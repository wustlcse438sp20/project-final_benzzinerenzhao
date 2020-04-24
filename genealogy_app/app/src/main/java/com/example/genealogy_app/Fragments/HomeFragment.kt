package com.example.genealogy_app.Fragments

import android.content.DialogInterface
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.genealogy_app.Activities.PersonalInfoActivity
import com.example.genealogy_app.DataClasses.*
import com.example.genealogy_app.FamilyTree
import com.example.genealogy_app.R
import com.example.genealogy_app.ViewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_tree_list.*
import java.util.*


class HomeFragment : Fragment(){

    val TAG = "HomeFragment.kt"
    var EDIT_CODE=0
    var OK_CODE=1
    //used for scrolling
    var downX = 0f
    var downY = 0f
    var xScrolled = 0f
    var yScrolled = 0f
    private lateinit var viewModel: HomeViewModel
    var tappedMember:Membership?=null
    var auth = FirebaseAuth.getInstance()
    var db = Firebase.firestore
    var email = auth.currentUser!!.email
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
            viewModel.currentTreeID=treeId


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
                        viewModel.currentAncestor=root
                        viewModel.currentTree = FamilyTree(root)
                        tree_view.setImageDrawable(viewModel.currentTree)
                        change_ancestor_button.setOnClickListener {

                            val view = requireActivity().layoutInflater.inflate(R.layout.dialog_change_ancestor, null)
                            val builder = AlertDialog.Builder(context!!)
                            val dialog: AlertDialog? = activity?.let {
                                builder.setView(view)
                                    // Add action buttons
                                    .setPositiveButton("Change Ancestor",
                                        DialogInterface.OnClickListener { dialog, id ->
                                            val ancestorNameBox = view.findViewById(R.id.ancestorName_field_change) as EditText
                                            val ancestorName = ancestorNameBox.text.toString()
                                            val ancestorSurnameBox = view.findViewById(R.id.ancestorSurname_field_change) as EditText
                                            val ancestorSurname = ancestorSurnameBox.text.toString()
                                            if (ancestorName != "" && ancestorSurname != "") {
                                                val newAncestor = Member(1)
                                                val p = Person(givenName = ancestorName,surname = ancestorSurname)
                                                newAncestor.person = p
                                                newAncestor.children = ArrayList<Member>()
                                                newAncestor.children!!.add(root)
                                                viewModel.currentAncestor = newAncestor
                                                viewModel.currentTree = FamilyTree(newAncestor)
                                                tree_view.setImageDrawable(viewModel.currentTree)
                                                val newTreeMap: MutableMap<String, Any> = HashMap()

                                                // Add a new document with a generated ID
                                                db.collection("trees").document(treeId)
                                                    .update("ancestor", newAncestor)

                                            }
                                        })
                                    .setNegativeButton("Cancel",
                                        DialogInterface.OnClickListener { dialog, id ->
                                            //user canceled dialog
                                            dialog.cancel()
                                        })
                                    .setTitle("Change Ancestor")
                                builder.create()
                            }
                            dialog!!.show()


                        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==EDIT_CODE){
            var bundle=data!!.extras
            if(bundle?.getString("firstName")!=null){
                var temp = bundle?.getString("firstName")
                tappedMember!!.person.givenName=temp!!
            }
            if(bundle?.getString("lastName")!=null){
                var temp = bundle?.getString("lastName")
                tappedMember!!.person.surname=temp!!
            }
            if(bundle?.getString("dob")!=null){
                var temp = bundle?.getString("dob")
                tappedMember!!.person.birthDate=Date(temp!!)
            }
            if(bundle?.getString("location")!=null){
                var temp = bundle?.getString("location")
                tappedMember!!.person.birthPlace=temp!!
            }
            if(bundle?.getString("bio")!=null){
                var temp = bundle?.getString("bio")
                tappedMember!!.person.biography=temp!!
            }
            db.collection("trees").document(viewModel.currentTreeID!!)
                .update("ancestor",viewModel.currentTree!!.mAncestor)
        }
        if(resultCode==OK_CODE){

        }
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
        viewModel.currentAncestor=donald
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
        tappedMember= locateTapped(viewModel.currentTree!!.mAncestor,pointTapped)
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
            startActivityForResult(personalInfoIntent,0)
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

    fun addAncestor(view: View){
    }


}
