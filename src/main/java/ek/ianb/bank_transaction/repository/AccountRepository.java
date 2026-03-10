package ek.ianb.bank_transaction.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class AccountRepository {

    private final JdbcTemplate jdbc;

    public AccountRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void withdraw(int id, BigDecimal amount) {
        jdbc.update(
                "UPDATE user_account SET balance = balance - ? WHERE account_id = ?",
                amount, id);
    }

    public void deposit(int id, BigDecimal amount) {

        int rows = jdbc.update(
                "UPDATE user_account SET balance = balance + ? WHERE account_id = ?",
                amount, id);

        if (rows == 0) {
            throw new IllegalArgumentException("Account not found");
        }
    }
}