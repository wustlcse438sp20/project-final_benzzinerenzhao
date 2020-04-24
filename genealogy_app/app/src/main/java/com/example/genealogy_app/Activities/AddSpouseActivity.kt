package com.example.genealogy_app.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.genealogy_app.R
import kotlinx.android.synthetic.main.activity_add_spouse.*
import kotlinx.android.synthetic.main.activity_edit_person.*

class AddSpouseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_spouse)
    }
    fun submitAddSpouse(view: View){
        var result = Intent()
        var hasName = true

        if(spouse_first_name!=null && spouse_first_name.text.toString() != ""){
            var spouseFirstName = spouse_first_name.text.toString()
            result.putExtra("spouseFirstName",spouseFirstName)
        }else{
            hasName=false
        }
        if(spouse_last_name!=null && spouse_last_name.text.toString() != ""){
            var spouseLastName = spouse_last_name.text.toString()
            result.putExtra("spouseLastName",spouseLastName)
        }else{
            hasName=false
        }
        if(hasName){
            setResult(0,result)
            this.finish()
        }else{
            Toast.makeText(baseContext, "You have to have both the first name and last name",
                Toast.LENGTH_SHORT).show()
        }

    }
}