package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.request.TodoSearchCond;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TodoRepositoryQuery {

    Optional<Todo> findByIdWithUserQuery(Long todoId);

    Page<TodoSearchResponse> searchTodos(TodoSearchCond cond, Pageable pageable);
}
