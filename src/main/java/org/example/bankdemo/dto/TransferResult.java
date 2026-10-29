package org.example.bankdemo.dto;

import org.example.bankdemo.entity.Account;
import org.example.bankdemo.entity.Transfer;

import java.math.BigDecimal;

public record TransferResult(Account from, Account to) {
    public static TransferResult from(Transfer transfer) {
        return new TransferResult(transfer.getFromAccount(),transfer.getToAccount());
    }
}
