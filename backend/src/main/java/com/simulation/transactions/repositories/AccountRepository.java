package com.simulation.transactions.repositories;

import com.simulation.transactions.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountModel, UUID> {
    Optional<AccountModel> findByNumAccount(String numAccount);

    boolean existsByNumAccount(String numAccount);
}
