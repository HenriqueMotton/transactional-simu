package com.simulation.transactions.services;

import com.simulation.transactions.dtos.ClientRecordDto;
import com.simulation.transactions.models.ClientModel;
import com.simulation.transactions.models.AccountModel;
import com.simulation.transactions.repositories.ClientRepository;
import com.simulation.transactions.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<ClientRecordDto> getAllClients() {
        return clientRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<ClientRecordDto> getClientById(UUID id) {
        return clientRepository.findById(id).map(this::convertToDTO);
    }

    public ClientRecordDto createCliente(ClientRecordDto clientRecordDto) {
        if (accountRepository.existsByNumAccount(clientRecordDto.numAccount())) {
            throw new IllegalArgumentException("Número da conta já existe na base.");
        }
        ClientModel client = new ClientModel();
        client.setName(clientRecordDto.name());
        client.setAge(clientRecordDto.age());
        client.setEmail(clientRecordDto.email());

        AccountModel account = new AccountModel();
        account.setNumAccount(clientRecordDto.numAccount());
        account.setBalance(BigDecimal.ZERO);
        account = accountRepository.save(account);

        client.setConta(account);

        ClientModel savedClient = clientRepository.save(client);
        return convertToDTO(savedClient);
    }

    private ClientRecordDto convertToDTO(ClientModel client) {
        return new ClientRecordDto(
                client.getName(),
                client.getAge(),
                client.getEmail(),
                client.getConta() != null ? client.getConta().getNumAccount() : null
        );
    }
}
