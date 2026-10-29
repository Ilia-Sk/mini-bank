package org.example.bankdemo.dto;

public record TransferResponse(AccountResponse fromAccount, AccountResponse toAccount) {

    public static TransferResponse from(TransferResult result) {
        return new TransferResponse (AccountResponse.from(result.from()), AccountResponse.from(result.to()));
    }
}
