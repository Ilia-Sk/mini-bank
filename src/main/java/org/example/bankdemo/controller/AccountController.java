package org.example.bankdemo.controller;

import jakarta.validation.Valid;
import org.example.bankdemo.dto.*;
import org.example.bankdemo.service.AccountService;
import org.example.bankdemo.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final TransferService transferService;

    public AccountController(AccountService accountService, TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return AccountResponse.from(accountService.create(createAccountRequest.owner()));
    }

    @GetMapping
    public List<AccountResponse> getAccounts() {
        return accountService.getAll().
                stream().
                map(AccountResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable Long id) {
        return AccountResponse.from(accountService.getById(id));
    }

    @PostMapping("/{id}/deposit")
    public AccountResponse deposit(@PathVariable Long id,@Valid @RequestBody AmountRequest request) {
        return AccountResponse.from(accountService.deposit(id, request.amount()));
    }

    @PostMapping("/{id}/withdraw")
    public AccountResponse withdraw(@PathVariable Long id,@Valid @RequestBody AmountRequest request) {
        return AccountResponse.from((accountService.withdraw(id,request.amount())));
    }

    @GetMapping("/{id}/transfers")
    public List<TransferHistoryItem> getHistory(@PathVariable Long id) {
        return transferService.getHistory(id).stream()
                .map(TransferHistoryItem::from)
                .toList();
    }
}
