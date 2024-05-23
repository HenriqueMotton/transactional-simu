package com.simulation.transactions.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "TB_ACCOUNTS")
public class AccountModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String numAccount;

    private BigDecimal balance = BigDecimal.ZERO;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumAccount() {
        return numAccount;
    }

    public void setNumAccount(String numAccount) {
        this.numAccount = numAccount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
