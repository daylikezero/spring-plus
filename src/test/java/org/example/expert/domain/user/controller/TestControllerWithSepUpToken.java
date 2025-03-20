package org.example.expert.domain.user.controller;

import org.example.expert.config.JwtAuthenticationToken;
import org.example.expert.config.JwtUtil;
import org.example.expert.config.SecurityConfig;
import org.example.expert.domain.auth.controller.TestController;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
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
public class TestControllerWithSepUpToken {

    @Autowired
    private MockMvc mockMvc;

    private JwtAuthenticationToken adminAuthenticationToken;
    private JwtAuthenticationToken userAuthenticationToken;

    /**
     *  2. 사전 설정된 토큰 재사용 방식
     * @BeforeEach를 사용하여 테스트 실행 전에 인증 토큰을 미리 생성하고 재사용
     */
    @BeforeEach
    public void setUp() {
        AuthUser adminUser = new AuthUser(1L, "admin@example.com", UserRole.ROLE_ADMIN, "관리자");
        adminAuthenticationToken = new JwtAuthenticationToken(adminUser);

        AuthUser commonUser = new AuthUser(2L, "user@example.com", UserRole.ROLE_USER, "사용자");
        userAuthenticationToken = new JwtAuthenticationToken(commonUser);
    }

    @Test
    public void 권한이_ADMIN일_경우_200() throws Exception {
        mockMvc.perform(get("/test")
                        .with(authentication(adminAuthenticationToken)))
                .andExpect(status().isOk());
    }

    @Test
    public void 권한이_USER일_경우_403() throws Exception {
        mockMvc.perform(get("/test")
                        .with(authentication(userAuthenticationToken)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void 토큰이_없을_경우() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isForbidden());
    }


}
