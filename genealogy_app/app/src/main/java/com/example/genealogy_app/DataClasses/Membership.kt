package com.example.genealogy_app.DataClasses

import android.graphics.RectF
import java.util.*

open class Membership{

    var x:Float=0.0f
    var y:Float=0.0f
    var width:Float=0.0f
    var height:Float=0.0f
    lateinit var personID:UUID
    lateinit var person:Person
    open fun getBounds(): RectF {
        return RectF(x, y, x + width, y + height)
    }

}