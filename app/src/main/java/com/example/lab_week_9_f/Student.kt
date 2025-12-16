package com.example.lab_week_9_f

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Student(
    val name: String
)
