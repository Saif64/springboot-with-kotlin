package com.example.demo.features.webflux

import com.fasterxml.jackson.annotation.JsonProperty

// Represents a single post from https://jsonplaceholder.typicode.com/posts
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

// Represents a single comment from https://jsonplaceholder.typicode.com/posts/1/comments
data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)

// This will be the final structure of your API's response
data class CombinedDataResponse(
    val posts: List<Post>,
    val comments: List<Comment>
)