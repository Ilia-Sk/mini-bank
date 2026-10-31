package org.example.bankdemo.controller;

import jakarta.validation.Valid;
import org.example.bankdemo.dto.*;
import org.example.bankdemo.entity.Transfer;
import org.example.bankdemo.repository.TransferRepository;
import org.example.bankdemo.service.AccountService;
import org.example.bankdemo.service.TransferService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final TransferService transferService;
    private final TransferRepository transferRepository;

    public AccountController(AccountService accountService, TransferService transferService, TransferRepository transferRepository) {
        this.accountService = accountService;
        this.transferService = transferService;
        this.transferRepository = transferRepository;
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
    public PageResponse<TransferHistoryItem> getHistory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return transferService.getHistory(id, page, size);
    }

    @GetMapping("/search")
    public PageResponse<AccountResponse> getAccountsThatConsistProvidedText(
            @RequestParam(required = false) String owner,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return accountService.searchAccountByName(owner, page, size);
    }
}
