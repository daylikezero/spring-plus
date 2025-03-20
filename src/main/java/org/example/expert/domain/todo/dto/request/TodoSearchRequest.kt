package org.example.expert.domain.todo.dto.request

import com.fasterxml.jackson.annotation.JsonFormat

class TodoSearchRequest(
    val title: String? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val startDate: String? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val endDate: String? = null,
    val nickname: String? = null,
)
