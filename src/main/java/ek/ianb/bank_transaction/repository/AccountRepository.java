package ek.ianb.bank_transaction.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void withdraw(int accountId, BigDecimal amount) {
        jdbcTemplate.update("UPDATE user_account SET balance = balance - ? WHERE account_id = ?", amount, accountId);
    }

    public void deposit(int accountId, BigDecimal amount) {
        int rows = jdbcTemplate.update("UPDATE user_account SET balance = balance + ? WHERE account_id = ?", amount, accountId);
        if (rows == 0) {
            throw new IllegalArgumentException("Account not found");
        }
    }
}