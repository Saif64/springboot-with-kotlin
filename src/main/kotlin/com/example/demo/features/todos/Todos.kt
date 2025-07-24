package com.example.demo.features.todos

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("todos")
data class Todos(
    @Id val id : ObjectId = ObjectId.get(),
    val title : String,
    val createdAt : Instant = Instant.now(),
    val isDone: Boolean = false,
)
