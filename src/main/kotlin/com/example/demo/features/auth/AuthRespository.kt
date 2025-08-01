package com.example.demo.features.auth

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface AuthRespository : MongoRepository<User, UUID> {
    fun findByUsername(username: String): User?
}