package org.example.bankdemo.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountRequest(@NotBlank String owner) {
}
