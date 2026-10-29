package org.example.bankdemo.dto;

import org.example.bankdemo.entity.Account;

import java.math.BigDecimal;

public record AccountResponse(Long id, String owner, BigDecimal balance) {

    public static AccountResponse from(Account account) {
        return new AccountResponse(account.getId(), account.getOwner(), account.getBalance());
    }
}
