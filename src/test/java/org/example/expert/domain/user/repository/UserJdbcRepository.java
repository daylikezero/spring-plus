package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.enums.UserRole;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Random;

@Repository
public class UserJdbcRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final int BATCH_SIZE = 5000;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(String[] nicknames, String password) {
        String sql = """
                INSERT INTO users (email, password, user_role, nickname, created_at, modified_at)
                        VALUES (?, ?, ?, ?, ?, ?)
                """;

        for (int i = 0; i < nicknames.length; i += BATCH_SIZE) {
            String[] batch = Arrays.copyOfRange(nicknames, i, i + BATCH_SIZE);

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int j) throws SQLException {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis() - new Random().nextLong(Integer.MAX_VALUE));
                    ps.setString(1, batch[j] + "@test.com");
                    ps.setString(2, password);
                    ps.setString(3, UserRole.ROLE_USER.name());
                    ps.setString(4, batch[j]);
                    ps.setTimestamp(5, timestamp);
                    ps.setTimestamp(6, timestamp);
                }

                @Override
                public int getBatchSize() {
                    return batch.length;
                }
            });
        }
    }
}