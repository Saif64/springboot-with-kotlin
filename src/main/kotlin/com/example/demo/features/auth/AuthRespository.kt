package com.example.demo.features.auth

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AuthRespository : MongoRepository<User, UUID> {
    fun findByUsername(username: String): User?
}