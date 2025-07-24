package com.example.demo.features.todos

import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/todos")
class TodosController(private val todoService: TodoService) {

    @PostMapping
    fun createTodo(@RequestBody payload: TodosPayload): TodosResponse {
        return todoService.createTodos(payload)
    }

    @GetMapping
    fun getAllTodos(): List<Todos> {
        return todoService.getAllTodos()
    }

    @DeleteMapping
    fun deleteAllTasks(): String {
        return todoService.deleteAllTasks()
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: UUID): TodosResponse? {
        return todoService.getTodoById(id)
    }

    @PatchMapping("/{id}")
    fun updateTodo(@PathVariable id: UUID, @RequestBody body: TodosUpdatePayload): TodosResponse? {
        return todoService.updateTodo(id, body)
    }
}