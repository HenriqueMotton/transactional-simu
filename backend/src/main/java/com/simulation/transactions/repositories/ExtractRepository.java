package com.simulation.transactions.repositories;

import com.simulation.transactions.models.AccountModel;
import com.simulation.transactions.models.ExtractModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ExtractRepository extends JpaRepository<ExtractModel, UUID> {
    @Query("select e from ExtractModel e join AccountModel a on e.account = a where a.numAccount = ?1 order by e.createdAt DESC")
    List<ExtractModel> listExtractByNumAccount(String numAccount);
}
