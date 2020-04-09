package com.example.genealogy_app.DataClasses

import java.util.*

data class Person(
    var id:UUID,
    var surname:String,
    var givenName:String,
    var gender:Gender,
    var birthDate:Date,
    var deathDate:Date,
    var birthPlace:String,
    var biography:String,
    var photoURL:String,
    var phoneNumber1:String,
    var phoneNumber2:String,
    var email:String,
    var familyID:UUID
)
enum class Gender{
    male,female,other
}