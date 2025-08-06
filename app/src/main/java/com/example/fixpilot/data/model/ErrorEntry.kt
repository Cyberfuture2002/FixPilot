package com.example.fixpilot.data.model

data class ErrorEntry(
    val category: String,
    val system: String,
    val program: String?,
    val errorCode: String,
    val description: String,
    val solution: String
)
