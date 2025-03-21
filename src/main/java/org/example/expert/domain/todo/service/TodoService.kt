package org.example.expert.domain.todo.service

import io.micrometer.common.util.StringUtils
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.example.expert.client.WeatherClient
import org.example.expert.domain.common.dto.AuthUser
import org.example.expert.domain.common.exception.InvalidRequestException
import org.example.expert.domain.todo.dto.request.TodoSaveRequest
import org.example.expert.domain.todo.dto.request.TodoSearchRequest
import org.example.expert.domain.todo.dto.response.TodoResponse
import org.example.expert.domain.todo.dto.response.TodoSaveResponse
import org.example.expert.domain.todo.dto.response.TodoSearchResponse
import org.example.expert.domain.todo.entity.Todo
import org.example.expert.domain.todo.repository.TodoRepository
import org.example.expert.domain.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class TodoService(
    private val todoRepository: TodoRepository,
    private val weatherClient: WeatherClient
) {
    @Transactional
    fun saveTodo(authUser: AuthUser, todoSaveRequest: TodoSaveRequest): TodoSaveResponse {
        val user = User.fromAuthUser(authUser)

        val weather = weatherClient.todayWeather

        val newTodo = Todo(
            title = todoSaveRequest.title,
            contents = todoSaveRequest.contents,
            weather = weather,
            user = user
        )
        val savedTodo = todoRepository.save(newTodo)
        return TodoSaveResponse.from(savedTodo)
    }

    fun getTodos(page: Int, size: Int, weather: String?, startDate: String?, endDate: String?): Page<TodoResponse> {
        val pageable: Pageable = PageRequest.of(page - 1, size)

        // Level1 - 3: getTodos 조건 검색
        val startDateTime = if (StringUtils.isEmpty(startDate)) null else LocalDate.parse(startDate).atStartOfDay()
        val endDateTime =
            if (StringUtils.isEmpty(endDate)) null else LocalDate.parse(endDate).atStartOfDay().plusDays(1)

        val todos = todoRepository.findAllByOrderByModifiedAtDesc(pageable, weather, startDateTime, endDateTime)

        return todos.map { todo: Todo -> TodoResponse.from(todo) }
    }

    fun getTodo(todoId: Long): TodoResponse {
        // Level2 - 8: QueryDSL
        val todo = todoRepository.findByIdWithUserQuery(todoId)
            .orElseThrow { InvalidRequestException("Todo not found") }

        return TodoResponse.from(todo)
    }

    fun searchTodos(page: Int, size: Int, cond: TodoSearchRequest): Page<TodoSearchResponse> {
        val pageable: Pageable = PageRequest.of(page - 1, size)
        val startDateTime =
            if (StringUtils.isEmpty(cond.startDate)) null else LocalDate.parse(cond.startDate).atStartOfDay()
        val endDateTime =
            if (StringUtils.isEmpty(cond.endDate)) null else LocalDate.parse(cond.endDate).atStartOfDay().plusDays(1)
        return todoRepository.searchTodos(pageable, cond.title, startDateTime, endDateTime, cond.nickname)
    }
}
