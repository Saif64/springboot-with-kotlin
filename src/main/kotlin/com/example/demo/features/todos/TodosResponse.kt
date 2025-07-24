package com.example.demo.features.todos

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.Instant

data class TodosResponse(
    val id: String,
    val title: String,
    val createdAt: Instant,
    val isDone: Boolean,
)

data class TodosPayload(
    val title: String
)

data class TodosUpdatePayload(
    val title: String?,
    val isDone: Boolean?
)
