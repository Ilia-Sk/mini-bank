package org.example.bankdemo.validation;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

public class ValidationUtils {

    public static void validateAmount(BigDecimal amount)  {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be positive");
        }
    }
}
