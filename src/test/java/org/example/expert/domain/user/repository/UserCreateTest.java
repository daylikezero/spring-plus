package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.utils.RandomCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class UserCreateTest {

    @Autowired
    private RandomCreator randomCreator;
    @Autowired
    private UserJdbcRepository userJdbcRepository;

    static final int TOTAL_LENGTH = 1_000_000;

    @Test
    void createUser() throws IOException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Set<String> nicknames = new HashSet<>(TOTAL_LENGTH);

        while (nicknames.size() < TOTAL_LENGTH) {
            String nickname = randomCreator.generateNickname();
            nicknames.add(nickname);
        }
        String password = encoder.encode("1111");

        userJdbcRepository.batchInsert(nicknames.stream().toArray(String[]::new), password);
    }
}
