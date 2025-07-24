package com.example.demo.features.todos

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TodosController(private  val todoService: TodoService) {

    @PostMapping("/create")
    fun createTodo(@RequestBody payload: TodosPayload) : TodosResponse {
        return  todoService.createTodos(payload)
    }

    @GetMapping
    fun getAllTodos() : List<Todos> {
        return todoService.getAllTodos()
    }
}