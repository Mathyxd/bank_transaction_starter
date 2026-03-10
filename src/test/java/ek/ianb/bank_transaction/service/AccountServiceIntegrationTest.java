package ek.ianb.bank_transaction.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AccountServiceIntegrationTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void transferMoney_commits_whenBothAccountsExist() {

        accountService.transferMoney(1, 2, new BigDecimal("100"));

        BigDecimal a = jdbcTemplate.queryForObject(
                "SELECT balance FROM user_account WHERE account_id = 1",
                BigDecimal.class);

        BigDecimal b = jdbcTemplate.queryForObject(
                "SELECT balance FROM user_account WHERE account_id = 2",
                BigDecimal.class);

        assertEquals(new BigDecimal("900.00"), a);
        assertEquals(new BigDecimal("2100.00"), b);
    }

    @Test
    void transferMoney_rollsBack_whenDestinationAccountDoesNotExist() {

        assertThrows(IllegalArgumentException.class, () ->
                accountService.transferMoney(1, 99, new BigDecimal("100"))
        );

        BigDecimal a = jdbcTemplate.queryForObject(
                "SELECT balance FROM user_account WHERE account_id = 1",
                BigDecimal.class);

        BigDecimal b = jdbcTemplate.queryForObject(
                "SELECT balance FROM user_account WHERE account_id = 2",
                BigDecimal.class);

        assertEquals(new BigDecimal("1000.00"), a);
        assertEquals(new BigDecimal("2000.00"), b);
    }
}