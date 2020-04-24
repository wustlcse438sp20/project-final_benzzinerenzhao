package com.example.genealogy_app.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.genealogy_app.DataClasses.Member
import com.example.genealogy_app.DataClasses.Person
import com.example.genealogy_app.DataClasses.Spouse
import com.example.genealogy_app.FamilyTree
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeViewModel (application: Application): AndroidViewModel(application){
    var currentTree:FamilyTree?=null

}


