package org.example.bankdemo.controller;

import jakarta.validation.Valid;
import org.example.bankdemo.dto.AccountResponse;
import org.example.bankdemo.dto.CreateAccountRequest;
import org.example.bankdemo.entity.Account;
import org.example.bankdemo.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.example.bankdemo.dto.AmountRequest;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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
}
