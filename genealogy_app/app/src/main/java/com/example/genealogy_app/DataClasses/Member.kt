package com.example.genealogy_app.DataClasses

import java.util.*

class Member(seniority:Int):Membership(){
    var id:UUID
    lateinit var fatherID:UUID
    lateinit var father:Membership
    lateinit var motherID:UUID
    lateinit var mother:Membership
    lateinit var familyID:UUID
    lateinit var family:Family
    var seniority:Int
    lateinit var children:ArrayList<Member>
    lateinit var spouses:ArrayList<Spouse>


    var depth:Int = 1
    var fullWidth:Float=0f
    var fullHeight:Float=0f
    var allChildrenAreSingle:Boolean = true
    init{
        id = UUID.randomUUID()
        this.seniority = seniority
    }
}