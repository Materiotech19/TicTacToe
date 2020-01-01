package com.materiotech.rajkumarnalluchamy.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var username: String? = "",
    var email: String? = "",
    var points: Int? = 0,
    var level: String? = ""
)