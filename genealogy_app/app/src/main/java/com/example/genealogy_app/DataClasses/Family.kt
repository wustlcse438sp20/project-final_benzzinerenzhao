package com.example.genealogy_app.DataClasses

import java.util.*

data class Family(
    var id:UUID,
    var surname:String,
    var ancestorID:UUID,
    var creator:String,
    var creatorID:UUID,
    var biography:String
)