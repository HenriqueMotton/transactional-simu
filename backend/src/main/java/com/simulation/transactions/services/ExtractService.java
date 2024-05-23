package com.simulation.transactions.services;

import com.simulation.transactions.dtos.ClientRecordDto;
import com.simulation.transactions.dtos.ExtractRecordDto;
import com.simulation.transactions.models.AccountModel;
import com.simulation.transactions.models.ExtractModel;
import com.simulation.transactions.repositories.AccountRepository;
import com.simulation.transactions.repositories.ExtractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExtractService {
    @Autowired
    private ExtractRepository extractRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<ExtractModel> getExtractsByAccountNumber(String numAccount) {
        AccountModel account = accountRepository.findByNumAccount(numAccount)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        List<ExtractModel> extracts = extractRepository.findByAccount(account);

        if (extracts.isEmpty()) {
            throw new RuntimeException("Nenhum extrato encontrado para a conta " + numAccount);
        }

        return extracts;
    }
}
