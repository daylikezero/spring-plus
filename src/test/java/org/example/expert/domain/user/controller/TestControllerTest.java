package org.example.expert.domain.user.controller;

import org.example.expert.config.JwtAuthenticationToken;
import org.example.expert.config.JwtUtil;
import org.example.expert.config.SecurityConfig;
import org.example.expert.domain.auth.controller.TestController;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
@Import({SecurityConfig.class, JwtUtil.class})
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 1. 기본 인증 토큰 주입 방식
     * JwtAuthenticationToken 을 생성하여 인증 객체를 직접 주입하는 방식
     */
    @Test
    public void 권한이_ADMIN일_경우_200() throws Exception {
        AuthUser authUser = new AuthUser(1L, "admin@example.com", UserRole.ROLE_ADMIN, "관리자");

        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authUser);

        mockMvc.perform(get("/test")
                .with(authentication(authenticationToken)))
                .andExpect(status().isOk());
    }

    @Test
    public void 권한이_USER일_경우_403() throws Exception {
        AuthUser authUser = new AuthUser(2L, "user@example.com", UserRole.ROLE_USER, "사용자");

        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authUser);

        mockMvc.perform(get("/test")
                        .with(authentication(authenticationToken)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void 토큰이_없을_경우() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isForbidden());
    }
}
