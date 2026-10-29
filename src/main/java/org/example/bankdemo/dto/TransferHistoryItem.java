package org.example.bankdemo.dto;

import org.example.bankdemo.entity.Transfer;

import java.math.BigDecimal;
import java.time.Instant;

public record TransferHistoryItem(Long id, Long fromId, Long toId, BigDecimal amount, Instant createdAt) {
    public static TransferHistoryItem from(Transfer transfer){
        return new TransferHistoryItem(transfer.getId(), transfer.getFromAccount().getId(), transfer.getToAccount().getId(), transfer.getAmount(), transfer.getCreatedAt());
    }
}
