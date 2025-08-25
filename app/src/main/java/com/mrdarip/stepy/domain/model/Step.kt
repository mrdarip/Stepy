package com.mrdarip.stepy.domain.model

data class Step(
    val id: Int = 0,
    val name: String,
    val position: Int,
    val taskId: Int
)
