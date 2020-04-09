package com.example.genealogy_app.DataClasses

import java.util.*

class Member(
    var id:UUID,
    var fatherID:UUID,
    var father:Membership,
    var motherID:UUID,
    var mother:Membership,
    var personID:UUID,
    var person:Person,
    var familyID:UUID,
    var family:Family,
    var seniority:Int,
    var children:ArrayList<Member>,
    var spouses:ArrayList<Spouse>,


    var depth:Int = 1,
    var fullWidth:Float,
    var fullHeight:Float,
    var x:Float=0.0f,
    var y:Float=0.0f,
    var width:Float,
    var height:Float,
    var allChildrenAreSingle:Boolean = true
):Membership()