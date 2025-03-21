package org.example.expert.domain.user.dto.response

import org.example.expert.domain.user.entity.User

data class UserResponse(
    val id: Long,
    val email: String
) {
    companion object {
        fun from(user: User) = UserResponse(user.id, user.email)
    }
}
