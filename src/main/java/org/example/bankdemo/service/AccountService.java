package org.example.bankdemo.service;

import org.example.bankdemo.dto.TransferResult;
import org.example.bankdemo.exception.AccountNotFoundException;
import org.example.bankdemo.exception.InsufficientFundsException;
import org.example.bankdemo.exception.InvalidTransferException;
import org.springframework.transaction.annotation.Transactional;
import org.example.bankdemo.entity.Account;
import org.example.bankdemo.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(String owner) {
        Account account = new Account();
        account.setOwner(owner);
        return accountRepository.save(account);
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account getById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Transactional
    public Account deposit(Long id, BigDecimal amount) {
        Account account = getById(id);
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    @Transactional
    public Account withdraw(Long id, BigDecimal amount) {
        Account account = getById(id);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient Funds");
        }
        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }

    @Transactional
    public TransferResult transfer(Long fromId, Long toId, BigDecimal amount) {
        if(fromId.equals(toId)) {
            throw new InvalidTransferException("Cannot transfer to the same account");
        }
        Account fromAccount = getById(fromId);
        Account toAccount = getById(toId);
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient Funds");
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        return new TransferResult(fromAccount, toAccount);}
}
