package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryQuery {

    @Query("""
            SELECT t
            FROM Todo t
            LEFT JOIN FETCH t.user u
            WHERE 1=1
            AND (:weather IS NULL OR t.weather LIKE CONCAT('%', :weather, '%'))
            AND (:startDateTime IS NULL OR t.modifiedAt >= :startDateTime)
            AND (:endDateTime IS NULL OR t.modifiedAt < :endDateTime)
            ORDER BY t.modifiedAt DESC
            """)
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable,
                                              @Param(value = "weather") String weather,
                                              @Param(value = "startDateTime") LocalDateTime startDateTime,
                                              @Param(value = "endDateTime") LocalDateTime endDateTime);
}
