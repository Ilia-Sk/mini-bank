package org.example.bankdemo.controller;

import jakarta.validation.Valid;
import org.example.bankdemo.dto.TransferRequest;
import org.example.bankdemo.dto.TransferResponse;
import org.example.bankdemo.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final AccountService accountService;

    public TransferController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping()
    public TransferResponse transfer(@RequestBody @Valid TransferRequest transferRequest) {
       return TransferResponse.from(accountService.transfer(transferRequest.fromId(), transferRequest.toId(), transferRequest.amount()));
    }
}
