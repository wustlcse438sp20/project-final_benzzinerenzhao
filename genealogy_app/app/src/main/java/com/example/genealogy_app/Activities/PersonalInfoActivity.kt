package com.example.genealogy_app.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.genealogy_app.R
import kotlinx.android.synthetic.main.activity_personal_info.*

class PersonalInfoActivity : AppCompatActivity() {
    var RequestEdit=1
    var EDIT_CODE=0
    var OK_CODE=1
    var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)
    }

    override fun onStart() {
        super.onStart()
        var bundle: Bundle? = intent.extras
        var firstName = (bundle?.getString("firstName")!!)
        var lastName = (bundle?.getString("lastName")!!)
        //var gender = (bundle?.getString("gender")!!)
        var DOB = (bundle?.getString("DOB")!!)
        var birthPlace = (bundle?.getString("birthPlace")!!)
        var biography = (bundle?.getString("biography")!!)

        //TODO: get extra of type UUID for the ID, and then pass it to the editPersonalInfo intent so we know exactly which person on the tree to edit
        //id = bundle?.get

        person_first_name.text="First Name: "+firstName
        person_last_name.text="Last Name: "+lastName
        //person_gender.text="Gender: "+gender
        person_dob.text="DOB: "+DOB
        person_birth_place.text="Location: "+birthPlace
        person_biography.text="Biography: "+biography
    }

    /*
    fun goBack(view: View){
        var result = Intent()
        setResult(OK_CODE,result)
        this.finish()
    }
    */
    fun editPersonalInfo(view: View){
        var editPersonIntent = Intent(this,EditPersonActivity::class.java)
        startActivityForResult(editPersonIntent,RequestEdit)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==EDIT_CODE){
            var bundle=data!!.extras
            var b = bundle!!.getString("firstName")
            var result = Intent()
            if(bundle?.getString("firstName")!=null){
                result.putExtra("firstName",bundle?.getString("firstName"))
            }
            if(bundle?.getString("lastName")!=null){
                result.putExtra("lastName",bundle?.getString("lastName"))
            }
            if(bundle?.getString("dob")!=null){
                result.putExtra("dob",bundle?.getString("dob"))
            }
            if(bundle?.getString("location")!=null){
                result.putExtra("location",bundle?.getString("location"))
            }
            if(bundle?.getString("bio")!=null){
                result.putExtra("bio",bundle?.getString("bio"))
            }
            setResult(EDIT_CODE,result)
            this.finish()
        }
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)

    }
}
