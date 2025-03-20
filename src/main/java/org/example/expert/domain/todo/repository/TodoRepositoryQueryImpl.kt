package org.example.expert.domain.todo.repository

import com.querydsl.core.types.Expression
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Wildcard
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import io.micrometer.common.util.StringUtils
import lombok.RequiredArgsConstructor
import org.example.expert.domain.todo.dto.response.TodoSearchResponse
import org.example.expert.domain.todo.entity.QTodo.todo
import org.example.expert.domain.todo.entity.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import java.time.LocalDateTime
import java.util.*

@RequiredArgsConstructor
class TodoRepositoryQueryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : TodoRepositoryQuery {

    override fun findByIdWithUserQuery(todoId: Long): Optional<Todo> {
        return Optional.ofNullable(
            jpaQueryFactory.selectFrom(todo)
                .leftJoin(todo.user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne()
        )
    }

    override fun searchTodos(
        pageable: Pageable,
        title: String?,
        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?,
        nickname: String?
    ): Page<TodoSearchResponse> {
        // 조회
        val query = findTodosQuery(
            Projections.constructor(
                TodoSearchResponse::class.java,
                todo.title,
                todo.managers.size(),  // 일정 담당자 수
                todo.comments.size()
            ),
            title,
            startDateTime,
            endDateTime,
            nickname
        )
            .orderBy(todo.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
        val todos = query.fetch()

        // 페이징 totalSize
        val totalSize = findTodosCountQuery(
            title,
            startDateTime,
            endDateTime,
            nickname
        ).fetch()[0]

        return PageableExecutionUtils.getPage(todos, pageable) { totalSize }
    }

    // kotlin convert
    override fun findAllByOrderByModifiedAtDesc(
        pageable: Pageable,
        weather: String?,
        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?
    ): Page<Todo> {
        val query = findTodosByWeather(todo, weather, startDateTime, endDateTime)
            .orderBy(todo.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
        val todos = query.fetch()
        val totalSize = findTodosCountByWeather(weather, startDateTime, endDateTime).fetch()[0]

        return PageableExecutionUtils.getPage(todos, pageable) { totalSize }
    }

    private fun <T> findTodosQuery(
        expr: Expression<T>,
        title: String?,
        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?,
        nickname: String?
    ): JPAQuery<T> {
        return jpaQueryFactory.select(expr)
            .from(todo)
            .where(
                todoTitleContains(title),
                todoCreatedAtAfter(startDateTime),
                todoCreatedAtBefore(endDateTime),
                todoManagerNicknameContains(nickname)
            )
    }

    private fun findTodosCountQuery(
        title: String?,
        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?,
        nickname: String?
    ): JPAQuery<Long> {
        return jpaQueryFactory.select(Wildcard.count)
            .from(todo)
            .where(
                todoTitleContains(title),
                todoCreatedAtAfter(startDateTime),
                todoCreatedAtBefore(endDateTime),
                todoManagerNicknameContains(nickname)
            )
    }

    private fun <T> findTodosByWeather(
        expr: Expression<T>,
        weather: String?,
        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?
    ): JPAQuery<T> {
        return jpaQueryFactory.select(expr)
            .from(todo)
            .leftJoin(todo.user).fetchJoin()
            .where(
                todoWeatherContains(weather),
                todoModifiedAtAfter(startDateTime),
                todoModifiedAtBefore(endDateTime)
            )
    }

    private fun findTodosCountByWeather(
        weather: String?,
        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?
    ): JPAQuery<Long> {
        return jpaQueryFactory.select(Wildcard.count)
            .from(todo)
            .where(
                todoWeatherContains(weather),
                todoModifiedAtAfter(startDateTime),
                todoModifiedAtBefore(endDateTime)
            )
    }

    private fun todoTitleContains(title: String?): BooleanExpression? {
        return if (StringUtils.isEmpty(title)) null else todo.title.contains(title)
    }

    private fun todoCreatedAtAfter(startDate: LocalDateTime?): BooleanExpression? {
        return if (Objects.nonNull(startDate)) todo.createdAt.after(startDate) else null
    }

    private fun todoCreatedAtBefore(endDate: LocalDateTime?): BooleanExpression? {
        return if (Objects.nonNull(endDate)) todo.createdAt.before(endDate) else null
    }

    private fun todoManagerNicknameContains(nickname: String?): BooleanExpression? {
        return if (StringUtils.isEmpty(nickname)) null else todo.managers.any().user.nickname.contains(nickname)
    }

    private fun todoWeatherContains(weather: String?): BooleanExpression? {
        return if (StringUtils.isEmpty(weather)) null else todo.weather.contains(weather)
    }

    private fun todoModifiedAtAfter(startDate: LocalDateTime?): BooleanExpression? {
        return if (Objects.nonNull(startDate)) todo.modifiedAt.after(startDate) else null
    }

    private fun todoModifiedAtBefore(endDate: LocalDateTime?): BooleanExpression? {
        return if (Objects.nonNull(endDate)) todo.modifiedAt.before(endDate) else null
    }
}
