package org.example.bankdemo.dto;

import java.util.Map;

public record ValidErrorResponse(int status, String message, Map<String,String> errors) {
}
