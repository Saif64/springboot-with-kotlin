package com.example.demo.features.todos

import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

interface TodoService {
    fun getAllTodos(): List<Todos>
    fun createTodos(body: TodosPayload): TodosResponse
    fun deleteAllTasks() : String
    fun updateTodo(id: UUID ,body: TodosUpdatePayload) : TodosResponse?
    fun getTodoById(id: UUID) : TodosResponse?

}

@Service
class TodosServiceImpl(private val todoRepository: TodoRepository) : TodoService {
    override fun getAllTodos(): List<Todos> {
        return todoRepository.findAll()
    }

    override fun createTodos(body: TodosPayload): TodosResponse {
        val todo = todoRepository.save(
            Todos(
                title = body.title,
                isDone = false,
            )
        )

        return TodosResponse(
            id = todo.id.toString(),
            title = todo.title,
            createdAt = todo.createdAt,
            isDone = todo.isDone,
        )
    }

    override fun deleteAllTasks(): String {
        todoRepository.deleteAll()
        return "All tasks deleted"
    }

    override fun getTodoById(id: UUID): TodosResponse? {
        return todoRepository.findById(id).map {
            TodosResponse(
                id = it.id.toString(),
                title = it.title,
                createdAt = it.createdAt,
                isDone = it.isDone,
            )
        }.orElse(null)
    }

    override fun updateTodo(id: UUID, body: TodosUpdatePayload): TodosResponse? {
        val existingTodo = todoRepository.findById(id).orElse(null) ?: return null

        val updatedTodo = existingTodo.copy(
            title = body.title ?: existingTodo.title,
            isDone = body.isDone ?: existingTodo.isDone,
        )

        val savedTodo = todoRepository.save(updatedTodo)

        return TodosResponse(
            id = savedTodo.id.toString(),
            title = savedTodo.title,
            createdAt = savedTodo.createdAt,
            isDone = savedTodo.isDone,
        )
    }

}