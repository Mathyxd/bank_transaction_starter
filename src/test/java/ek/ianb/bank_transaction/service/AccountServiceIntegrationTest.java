package ek.ianb.bank_transaction.service;

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
        // Act
        // 1. Call the transferMoney method to transfer 100 from account with account_id = 1 to account with account_id = 2
        accountService.transferMoney(1,2,BigDecimal.valueOf(100));
        // 2. Call the jdbcTemplate.queryForObject method to obtain the actual balance from user_account where account_id = 1
        Integer balance1 = jdbcTemplate.queryForObject("SELECT balance FROM user_account WHERE account_id = ?", Integer.class, 1);
        // 3. Call the jdbcTemplate.queryForObject method to obtain the actual balance from user_account where account_id = 2
        Integer balance2 = jdbcTemplate.queryForObject("SELECT balance FROM user_account WHERE account_id = ?", Integer.class, 2);

        // Assert
        // 4. Assert that the balance in account with account_id = 1 is as expected
        assertEquals(900, balance1);
        // 5. Assert that the balance in account with account_id = 2 is as expected
        assertEquals(2100, balance2);

    }

    @Test
    void transferMoney_rollsBack_whenDestinationAccountDoesNotExist() {

        // Act and assert
        // 1. Assert that the accountService.transferMoney method throws an IllegalArgumentException exception
        //    when trying to transfer money to an account_id which does not exist 
        // (Hint: use assertThrows(). The second argument to assertThrows should be
        //  () -> accountService.transferMoney()
        // The arguments for transferMoney are not shown but must be supplied
        // 2. Call the jdbcTemplate.queryForObject method to obtain the actual balance from user_account where account_id = 1
        // 3. Call the jdbcTemplate.queryForObject method to obtain the actual balance from user_account where account_id = 2
        // Assert
        // 4. Assert that the balance in account with account_id = 1 is as expected
        // 5. Assert that the balance in account with account_id = 2 is as expected

    }
}
