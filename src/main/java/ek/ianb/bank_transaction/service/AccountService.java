package ek.ianb.bank_transaction.service;

import ek.ianb.bank_transaction.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void transferMoney(int fromId, int toId, BigDecimal amount) {
        repo.withdraw(fromId, amount);
        repo.deposit(toId, amount);
    }
}
