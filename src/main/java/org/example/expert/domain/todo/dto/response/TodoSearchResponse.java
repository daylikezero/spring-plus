package org.example.expert.domain.todo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class TodoSearchResponse {
    private final String title;
    private final Integer managerCount;
    private final Integer commentsCount;

    @QueryProjection
    public TodoSearchResponse(String title, Integer managerCount, Integer commentsCount) {
        this.title = title;
        this.managerCount = managerCount;
        this.commentsCount = commentsCount;
    }
}
