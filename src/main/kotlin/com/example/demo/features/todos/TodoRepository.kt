package com.example.demo.features.todos

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : MongoRepository<Todos, ObjectId > {
    fun filterByStatus(status: Boolean) : List<Todos>
}