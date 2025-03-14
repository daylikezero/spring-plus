package org.example.expert.domain.user.dto.response;

import lombok.Getter;
import org.example.expert.domain.user.entity.User;

@Getter
public class UserUpdateResponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final String profileUrl;

    public UserUpdateResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileUrl = user.getProfileUrl();
    }
}
