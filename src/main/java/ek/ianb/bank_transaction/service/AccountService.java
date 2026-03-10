package ek.ianb.bank_transaction.service;

import ek.ianb.bank_transaction.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transferMoney(int fromAccountId, int toAccountID, BigDecimal amount) {
        accountRepository.withdraw(fromAccountId, amount);
        accountRepository.deposit(toAccountID, amount);
    }
}
