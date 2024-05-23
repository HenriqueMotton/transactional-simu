package com.simulation.transactions.repositories;

import com.simulation.transactions.models.AccountModel;
import com.simulation.transactions.models.ExtractModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExtractRepository extends JpaRepository<ExtractModel, UUID> {
    List<ExtractModel> findByAccount(AccountModel account);
}
