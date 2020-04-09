package com.example.genealogy_app.DataClasses

import java.util.*

data class Spouse(
    var affiliation:Member,
    var affiliationID:UUID,
    var personID:UUID,
    var person:Person,
    var ranking:Int,




    var x:Float=0.0f,
    var y:Float=0.0f,
    var width:Float,
    var height:Float
):Membership()