package com.example.genealogy_app.DataClasses

import java.util.*

class Member(seniority:Int):Membership(){
    var id:UUID
    //lateinit var fatherID:UUID
    var father:Membership?=null
    //lateinit var motherID:UUID
    var mother:Membership?=null
    //lateinit var familyID:UUID
    //lateinit var family:Family
    var seniority:Int
    var children:ArrayList<Member>?=null
    var spouses:ArrayList<Spouse>?=null


    var depth:Int = 1
    var fullWidth:Float=0f
    var fullHeight:Float=0f
    var allChildrenAreSingle:Boolean = true
    init{
        id = UUID.randomUUID()
        this.seniority = seniority
    }
}