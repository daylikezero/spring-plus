package org.example.expert.domain.todo.dto.response

import org.example.expert.domain.todo.entity.Todo
import org.example.expert.domain.user.dto.response.UserResponse
import java.time.LocalDateTime

class TodoResponse(
    val id: Long,
    val title: String,
    val contents: String,
    val weather: String,
    val user: UserResponse,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {
    companion object {
        fun from(todo: Todo) = with(todo) {
            TodoResponse(id,
                title,
                contents,
                weather,
                UserResponse.from(user),
                createdAt,
                modifiedAt
            )
        }
    }
}
