package org.example.bankdemo.dto;

import java.math.BigDecimal;

public record AccountSummaryResponse(Long accountId, String owner, BigDecimal balance, Long sentCount, Long receivedCount, BigDecimal totalSend,BigDecimal
totalResend) {
}
