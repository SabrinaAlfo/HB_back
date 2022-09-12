package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;
import java.util.Set;

public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;
    private Set<AccountDTO> accounts;
    private Set<ClientDTO> clients;

    //constructores
    public TransactionDTO() {
    }

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
    }

    //getters
    public TransactionType getType() {return type;}
    public double getAmount() {return amount;}
    public Long getId() {return id;}
    public String getDescription() {return description;}
    public LocalDateTime getDate() {return date;}
    public Set<AccountDTO> getAccounts() {return accounts;}
}
