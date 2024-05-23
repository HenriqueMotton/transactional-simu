package com.simulation.transactions.controller;

import com.simulation.transactions.dtos.ClientRecordDto;
import com.simulation.transactions.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientRecordDto>> getAllClients() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAllClients());
    }

    @PostMapping
    public ResponseEntity<?> saveClient(@RequestBody @Valid ClientRecordDto clientRecordDto){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createCliente(clientRecordDto));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
