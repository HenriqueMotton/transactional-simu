package com.simulation.transactions.controller;

import com.simulation.transactions.dtos.ExtractRecordDto;
import com.simulation.transactions.enums.TransactionType;
import com.simulation.transactions.models.AccountModel;
import com.simulation.transactions.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{numAccount}")
    public ResponseEntity<?> getAccountByNum(@PathVariable String numAccount){
        try{
            return ResponseEntity.ok(accountService.getAccountByNum(numAccount));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{numAccount}")
    public ResponseEntity<?> updateAccountBalance(@PathVariable String numAccount, @Valid @RequestBody ExtractRecordDto extractRecordDto) {
        try {
            accountService.updateAccountBalance(numAccount, extractRecordDto.value(), extractRecordDto.type());
            String tipoTransacao = extractRecordDto.type() == TransactionType.DEBIT ? "Débito" : "Crédito";
            String message = String.format("%s no valor de R$%.2f feito com sucesso na conta de número %s",
                    tipoTransacao, extractRecordDto.value(), numAccount);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
