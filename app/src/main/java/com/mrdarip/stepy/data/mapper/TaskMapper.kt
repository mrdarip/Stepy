package com.mrdarip.stepy.data.mapper

import com.mrdarip.stepy.data.local.entities.TaskEntity
import com.mrdarip.stepy.domain.model.Task

fun TaskEntity.toDomain(): Task {
    return Task(id = id, name = name)
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(id = id, name = name)
}