package com.simulation.transactions.services;

import com.simulation.transactions.enums.TransactionType;
import com.simulation.transactions.models.AccountModel;
import com.simulation.transactions.models.ExtractModel;
import com.simulation.transactions.repositories.AccountRepository;
import com.simulation.transactions.repositories.ExtractRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    @Autowired
    private AccountRepository contaRepository;

    @Autowired
    private ExtractRepository extractRepository;

    public AccountModel getAccountByNum(String numAccount){
        AccountModel account = contaRepository.findByNumAccount(numAccount)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        return account;
    }

    @Transactional
    public void updateAccountBalance(String numAccount, BigDecimal value, TransactionType type) {
        AccountModel account = contaRepository.findByNumAccount(numAccount)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        if (type == TransactionType.DEBIT) {
            account.setBalance(account.getBalance().subtract(value));
        } else if (type == TransactionType.CREDIT) {
            account.setBalance(account.getBalance().add(value));
        }

        contaRepository.save(account);

        ExtractModel extract = new ExtractModel();
        extract.setType(type);
        extract.setValue(value);
        extract.setAccount(account);

        extractRepository.save(extract);
    }
}
