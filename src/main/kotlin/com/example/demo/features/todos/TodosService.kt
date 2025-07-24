package com.example.demo.features.todos

import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.Instant

interface TodoService {
    fun getAllTodos() : List<Todos>
    fun createTodos(body: TodosPayload) : TodosResponse

}

@Service
class TodosServiceImpl(private  val todoRepository: TodoRepository) : TodoService{
    override fun getAllTodos() : List<Todos> {
        return  todoRepository.findAll()
    }

    override fun createTodos(body: TodosPayload): TodosResponse {
       val todo =   todoRepository.save(Todos(
            id = ObjectId.get(),
            title = body.title,
        ))

        return TodosResponse(
            id = todo.id,
            title = todo.title,
            createdAt = Instant.now(),
            isDone = false  ,
        )
    }

}