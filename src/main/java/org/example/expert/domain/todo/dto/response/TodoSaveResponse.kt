package org.example.expert.domain.todo.dto.response

import org.example.expert.domain.todo.entity.Todo
import org.example.expert.domain.user.dto.response.UserResponse

class TodoSaveResponse(
    val id: Long,
    val title: String,
    val contents: String,
    val weather: String,
    val user: UserResponse
) {
    companion object {
        fun from(todo: Todo) = with(todo) {
            TodoSaveResponse(
                id!!,
                title,
                contents,
                weather,
                UserResponse.from(user)
            )
        }
    }
}
