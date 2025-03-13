package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.request.TodoSearchCond;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static org.example.expert.domain.todo.entity.QTodo.todo;

@RequiredArgsConstructor
public class TodoRepositoryQueryImpl implements TodoRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Todo> findByIdWithUserQuery(Long todoId) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(todo)
                .leftJoin(todo.user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne());
    }

    @Override
    public Page<TodoSearchResponse> searchTodos(TodoSearchCond cond, Pageable pageable) {
        // 조회
        var query = findTodosQuery(Projections.constructor(TodoSearchResponse.class,
                todo.title,
                todo.managers.size(), // 일정 담당자 수
                todo.comments.size()), cond)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        var todos = query.fetch();

        // 페이징 totalSize
        long totalSize = findTodosCountQuery(cond).fetch().get(0);

        return PageableExecutionUtils.getPage(todos, pageable, () -> totalSize);
    }

    private <T> JPAQuery<T> findTodosQuery(Expression<T> expr, TodoSearchCond cond) {
        return jpaQueryFactory.select(expr)
                .from(todo)
                .where(
                        todoTitleContains(cond.getTitle()),
                        todoCreateAtAfter(cond.getStartDate()),
                        todoCreateAtBefore(cond.getEndDate()),
                        todoManagerNicknameContains(cond.getNickname())
                );
    }

    private JPAQuery<Long> findTodosCountQuery(TodoSearchCond cond) {
        return jpaQueryFactory.select(Wildcard.count)
                .from(todo)
                .where(
                        todoTitleContains(cond.getTitle()),
                        todoCreateAtAfter(cond.getStartDate()),
                        todoCreateAtBefore(cond.getEndDate()),
                        todoManagerNicknameContains(cond.getNickname())
                );
    }

    private BooleanExpression todoTitleContains(String title) {
        return StringUtils.isEmpty(title) ? null : todo.title.contains(title);
    }

    private BooleanExpression todoCreateAtAfter(LocalDate startDate) {
        return Objects.nonNull(startDate) ? todo.createdAt.after(startDate.atStartOfDay()) : null;
    }

    private BooleanExpression todoCreateAtBefore(LocalDate endDate) {
        return Objects.nonNull(endDate) ? todo.createdAt.before(endDate.plusDays(1).atStartOfDay()) : null;
    }

    private BooleanExpression todoManagerNicknameContains(String nickname) {
        return StringUtils.isEmpty(nickname) ? null : todo.managers.any().user.nickname.contains(nickname);
    }
}
