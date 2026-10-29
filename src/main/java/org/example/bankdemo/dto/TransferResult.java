package org.example.bankdemo.dto;

import org.example.bankdemo.entity.Account;

public record TransferResult(Account from, Account to) {

    @Override
    public Account from() {
        return from;
    }

    @Override
    public Account to() {
        return to;
    }
}
