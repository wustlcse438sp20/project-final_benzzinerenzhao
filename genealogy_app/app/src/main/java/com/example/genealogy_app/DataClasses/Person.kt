package com.example.genealogy_app.DataClasses

import java.util.*

data class Person(
    var id:UUID,
    var surname:String,
    var givenName:String,
    var gender:Gender?=null,
    var birthDate:Date?=null,
    //var deathDate:Date?=null,
    var birthPlace:String?=null,
    var biography:String?=null/*,
    var photoURL:String,
    var phoneNumber1:String,
    var phoneNumber2:String,
    var email:String,
    var familyID:UUID*/
)
enum class Gender{
    male,female,other
}