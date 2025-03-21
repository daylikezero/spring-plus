package org.example.expert.domain.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.expert.domain.auth.dto.request.SigninRequest;
import org.example.expert.domain.auth.dto.request.SignupRequest;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void 회원가입과_로그인_후_ADMIN_인가를_통과하고_유저_정보를_확인한다() throws Exception {
        String adminEmail = "admin@example.com";
        String adminPassword = "1111";

        MockMultipartFile signUpRequest = new MockMultipartFile(
                "data",
                "data.json",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(new SignupRequest(adminEmail,
                        adminPassword,
                        UserRole.Authority.ADMIN,
                        "admin")));

        Path imagePath = Paths.get("src/test/resources/test.jpg");
        byte[] imageBytes = Files.readAllBytes(imagePath);
        MockMultipartFile imageFile = new MockMultipartFile("file",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                imageBytes);

        // 1. 회원가입
        mockMvc.perform(MockMvcRequestBuilders.multipart("/auth/signup")
                        .file(signUpRequest)
                        .file(imageFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .characterEncoding("UTF-8")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        // 2. 로그인
        SigninRequest signinRequest = new SigninRequest(adminEmail, adminPassword);
        MvcResult mvcResult = mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signinRequest))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(result -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
                    String bearerToken = jsonNode.get("bearerToken").asText();
                    result.getResponse().setHeader("Authorization", bearerToken);
                    assertThat(bearerToken).isNotNull();
                })
                .andReturn();

        String bearerToken = mvcResult.getResponse().getHeader("Authorization");

        // 3. /test 엔드포인트 호출
        mockMvc.perform(get("/test")
                        .header("Authorization", bearerToken))
                .andExpect(status().isOk());
    }
}
