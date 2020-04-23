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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)
    }

    override fun onStart() {
        super.onStart()
        var bundle: Bundle? = intent.extras
        var firstName = (bundle?.getString("firstName")!!)
        var lastName = (bundle?.getString("lastName")!!)
        var gender = (bundle?.getString("gender")!!)
        var DOB = (bundle?.getString("DOB")!!)
        var birthPlace = (bundle?.getString("birthPlace")!!)
        var biography = (bundle?.getString("biography")!!)
        person_first_name.text="First Name: "+firstName
        person_last_name.text="Last Name: "+lastName
        person_gender.text="Gender: "+gender
        person_dob.text="DOB: "+DOB
        person_birth_place.text="Birth Place: "+birthPlace
        person_biography.text="Biography: "+biography
    }
    fun goBack(view: View){
       this.finish()
    }
}
