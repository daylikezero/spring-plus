package org.example.expert.domain.user.controller;

import org.example.expert.config.JwtUtil;
import org.example.expert.config.SecurityConfig;
import org.example.expert.config.WithMockAuthUser;
import org.example.expert.domain.auth.controller.TestController;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
@Import({SecurityConfig.class, JwtUtil.class})
public class TestControllerWithMockAuthUser {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 3. 커스텀 어노테이션 방식
     * @WithMockAuthUser 와 같은 커스텀 어노테이션을 생성하여 테스트 메서드에 직접 인증 정보를 설정하는 방식
     */
    @Test
    @WithMockAuthUser(userId = 1L, email = "admin@example.com", role = UserRole.ROLE_ADMIN, nickname = "관리자")
    public void 권한이_ADMIN일_경우_200() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockAuthUser(userId = 2L, email = "user@example.com", role = UserRole.ROLE_USER, nickname = "사용자")
    public void 권한이_USER일_경우_403() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void 토큰이_없을_경우() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isForbidden());
    }
}
