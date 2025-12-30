package com.mrdarip.stepy.domain.model

data class Step(
    val id: Long = 0,
    val name: String,
    val position: Int?,
    val taskId: Long,
    val skippable: Boolean
)
