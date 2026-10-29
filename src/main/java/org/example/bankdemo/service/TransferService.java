package org.example.bankdemo.service;

import org.example.bankdemo.dto.TransferResult;
import org.example.bankdemo.entity.Account;
import org.example.bankdemo.entity.Transfer;
import org.example.bankdemo.exception.AccountNotFoundException;
import org.example.bankdemo.exception.InsufficientFundsException;
import org.example.bankdemo.exception.InvalidTransferException;
import org.example.bankdemo.repository.AccountRepository;
import org.example.bankdemo.repository.TransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    public TransferService(TransferRepository transferRepository, AccountRepository accountRepository, AccountService accountService) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
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
        return TransferResult.from(transferRepository.save(new Transfer(getById(fromId),getById(toId),amount)));
    }

    @Transactional
    public List<Transfer> getHistory(Long accountId) {
        if(!accountRepository.existsById(accountId)) {
            throw new AccountNotFoundException("Account not found");
        }
        return transferRepository.findByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(accountId,accountId);
    }


    private Account getById(Long Id) {
        return accountRepository.findById(Id).orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }
}

