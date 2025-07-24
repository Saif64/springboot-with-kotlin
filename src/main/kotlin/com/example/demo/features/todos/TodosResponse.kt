package com.example.demo.features.todos

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.Instant

data class TodosResponse(
    @Id val id: ObjectId,
    val title: String,
    val createdAt: Instant,
    val isDone: Boolean,
)

data class TodosPayload(
    val id: ObjectId,
    val title: String
)
