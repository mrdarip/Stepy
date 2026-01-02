package com.mrdarip.stepy.domain.model

data class Task(
    val id: Long = 0,
    val name: String,
    val favorite: Boolean = false
)