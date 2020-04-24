package com.example.genealogy_app.DataClasses

import java.util.*

class Spouse(ranking:Int):Membership() {
    lateinit var affiliation: Member
    lateinit var affiliationID: UUID
    var ranking: Int

    init{
        this.ranking = ranking
    }



}