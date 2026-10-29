package org.example.bankdemo.controller;

import jakarta.validation.Valid;
import org.example.bankdemo.dto.TransferRequest;
import org.example.bankdemo.dto.TransferResponse;
import org.example.bankdemo.service.AccountService;
import org.example.bankdemo.service.TransferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final TransferService transferService;

    public TransferController(AccountService accountService, TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping()
    public TransferResponse transfer(@RequestBody @Valid TransferRequest transferRequest) {
       return TransferResponse.from(transferService.transfer(transferRequest.fromId(), transferRequest.toId(), transferRequest.amount()));
    }
}
