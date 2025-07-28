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
    fun deleteTaskById(id: UUID) : String?
    fun getTodoById(id: UUID) : TodosResponse?
    fun getTodoByStatus(isDone: Boolean) : List<TodosResponse>?

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

        return todo.toResponse()
    }

    override fun deleteAllTasks(): String {
        todoRepository.deleteAll()
        return "All tasks deleted"
    }

    override fun deleteTaskById(id: UUID): String? {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id)
            return "Successfully deleted todo with id '$id'"
        } else {
            return null
        }
    }


    override fun getTodoById(id: UUID): TodosResponse? {
        return todoRepository.findById(id).map { it.toResponse() }.orElse(null)
    }

    override fun updateTodo(id: UUID, body: TodosUpdatePayload): TodosResponse? {
        val existingTodo = todoRepository.findById(id).orElse(null) ?: return null

        val updatedTodo = existingTodo.copy(
            title = body.title ?: existingTodo.title,
            isDone = body.isDone ?: existingTodo.isDone,
        )

        val savedTodo = todoRepository.save(updatedTodo)

        return savedTodo.toResponse()
    }

    override fun getTodoByStatus(isDone: Boolean): List<TodosResponse>? {
        return todoRepository.findByIsDone(isDone)
            .map { it.toResponse() }
    }

    private fun Todos.toResponse(): TodosResponse = TodosResponse(
        id = this.id.toString(),
        title = this.title,
        createdAt = this.createdAt,
        isDone = this.isDone
    )
}