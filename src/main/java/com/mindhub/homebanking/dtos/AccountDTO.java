package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private Set<TransactionDTO> transactions;

    //constructor vacío
    public AccountDTO() {
    }

    //Contructor
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(transaction ->new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    public long getId() {return id;}
     //Getters
    public String getNumber() {
        return number;
    }
    public LocalDateTime getCreationDate() {return creationDate;}
    public double getBalance() {return balance;}
    public Set<TransactionDTO> getTransactions() { return transactions;}
    public void setTransactions(Set<TransactionDTO> transactions) {this.transactions = transactions;}

    //setteer

    public void setNumber(String number) {
        this.number = number;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
}