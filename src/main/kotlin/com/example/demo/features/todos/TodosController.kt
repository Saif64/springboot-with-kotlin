package com.example.demo.features.todos

import com.example.demo.util.ApiResponse
import com.example.demo.util.buildSuccessResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun createTodo(@RequestBody payload: TodosPayload): ResponseEntity<ApiResponse<TodosResponse>> {
        val createdTodo = todoService.createTodos(payload)
        return buildSuccessResponse(createdTodo, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllTodos(): ResponseEntity<ApiResponse<List<Todos>>> {
        return buildSuccessResponse(todoService.getAllTodos())
    }

    @DeleteMapping
    fun deleteAllTasks(): ResponseEntity<ApiResponse<String>> {
        return buildSuccessResponse(todoService.deleteAllTasks())
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: UUID): ResponseEntity<ApiResponse<TodosResponse?>> {
        return buildSuccessResponse(todoService.getTodoById(id))
    }

    @PatchMapping("/{id}")
    fun updateTodo(
        @PathVariable id: UUID,
        @RequestBody body: TodosUpdatePayload
    ): ResponseEntity<ApiResponse<TodosResponse?>> {
        return buildSuccessResponse(todoService.updateTodo(id, body))
    }

    @GetMapping("/status/{isDone}")
    fun getTodoByStatus(@PathVariable isDone: Boolean): ResponseEntity<ApiResponse<List<TodosResponse>?>> =
        buildSuccessResponse(
            todoService.getTodoByStatus(
                isDone = isDone
            )
        )
}