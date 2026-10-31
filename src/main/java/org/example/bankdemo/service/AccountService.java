package org.example.bankdemo.service;

import org.example.bankdemo.dto.AccountResponse;
import org.example.bankdemo.dto.PageResponse;
import org.example.bankdemo.exception.AccountNotFoundException;
import org.example.bankdemo.exception.InsufficientFundsException;
import org.example.bankdemo.exception.InvalidOwnerException;
import org.example.bankdemo.exception.InvalidPageRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Transactional(readOnly = true)
    public PageResponse<AccountResponse> searchAccountByName(String name, int page, int size) {
        if (name == null || name.isBlank()) {
            throw new InvalidOwnerException("Owner must not be blank");
        }
        if(page < 0) {
            throw new InvalidPageRequestException("Page must be greater than or equal to 0");
        }
        if(size <1 || size >50){
            throw new InvalidPageRequestException("Size must be between 1 and 50");
        }
        Page<Account> result =accountRepository.findByOwnerContainingIgnoreCase(name, PageRequest.of(page, size));
        List<AccountResponse> items =result.getContent().
                stream().
                map(AccountResponse::from).
                toList();
        return new PageResponse<>(
                items,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }
}
