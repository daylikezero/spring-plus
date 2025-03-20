package org.example.expert.domain.todo.repository

import org.example.expert.domain.todo.dto.response.TodoSearchResponse
import org.example.expert.domain.todo.entity.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime
import java.util.*

interface TodoRepositoryQuery {
    fun findByIdWithUserQuery(todoId: Long): Optional<Todo>

    fun searchTodos(
        pageable: Pageable,
        title: String?,
        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?,
        nickname: String?
    ): Page<TodoSearchResponse>

    fun findAllByOrderByModifiedAtDesc(
        pageable: Pageable,
        weather: String?,
        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?
    ): Page<Todo>
}
