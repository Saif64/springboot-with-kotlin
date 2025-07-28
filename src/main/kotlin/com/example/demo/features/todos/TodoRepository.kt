package com.example.demo.features.todos

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TodoRepository : MongoRepository<Todos, UUID > {
    fun findByIsDone(isDone: Boolean): List<Todos>
}