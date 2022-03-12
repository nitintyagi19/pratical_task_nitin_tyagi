package com.app.tasknitintyagi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SignUpModel(
    var fullName: String,
    @PrimaryKey var email: String,
    var mobile: String,
    var gender: String,
    var city: String,
    var password: String
)
