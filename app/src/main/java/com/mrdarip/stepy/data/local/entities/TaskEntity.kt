package com.mrdarip.stepy.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String
)