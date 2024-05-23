package com.simulation.transactions.controller;

import com.simulation.transactions.dtos.ClientRecordDto;
import com.simulation.transactions.dtos.ExtractRecordDto;
import com.simulation.transactions.models.ExtractModel;
import com.simulation.transactions.services.ClientService;
import com.simulation.transactions.services.ExtractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("extracts")
@RequiredArgsConstructor
public class ExtractController {
    private final ExtractService extractService;

    @GetMapping("/{numAccount}")
    public ResponseEntity<?> getExtractsByAccountNumber(@PathVariable String numAccount) {
        try{
            List<ExtractModel> extracts = extractService.getExtractsByAccountNumber(numAccount);
            return ResponseEntity.ok(extracts);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
