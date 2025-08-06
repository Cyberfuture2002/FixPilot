package com.example.fixpilot.data.model

import com.example.fixpilot.data.model.Answer

data class Question(
    val id: String,
    val text: String,
    val answers: List<Answer>
)
