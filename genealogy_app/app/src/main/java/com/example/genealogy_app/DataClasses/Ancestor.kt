package com.example.genealogy_app.DataClasses

import java.util.*

data class Ancestor(
    //var id: UUID,
    var father:Membership?=null,
    var mother:Membership?=null,
    var seniority:Int=1,
    var children: ArrayList<Member>?=null,
    var spouses: ArrayList<Spouse>?=null,
    var depth:Int = 1,
    var fullWidth:Float=0f,
    var fullHeight:Float=0f,
    var allChildrenAreSingle:Boolean = true,
    var bounds:Bounds?=null,
    var person:Person?=null,
    var x:Float=0.0f,
    var y:Float=0.0f,
    var width:Float=0.0f,
    var height:Float=0.0f

)