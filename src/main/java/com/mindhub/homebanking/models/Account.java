package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Account {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(name = "native", strategy = "native")

        private long id;
        private String number;
        private LocalDateTime creationDate;
        private double balance;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "client_id")
        private Client client;

        @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
        private Set<Transaction> transactions = new HashSet<>();


        public Account() {
        }

        public Set<Transaction> getTransactions() {
                return transactions;
        }
        public void setTransactions(Set<Transaction> transactions) {
                this.transactions = transactions;
        }
        public Account(Set<Transaction> transactions) {
                this.transactions = transactions;
        }
        public void addTransaction(Transaction transaction) {
                transaction.setAccount(this);
                transactions.add(transaction);
        }

        @JsonIgnore
        public Client getClient() {
                return client;
        }
        public void setClient(Client client) {
                this.client = client;
        }
        public long getId() {
                return id;
        }
        public String getNumber() {
                return number;
        }
        public void setNumber(String number) {
                number = number;
        }
        public LocalDateTime getCreationDate() {
                return creationDate;
        }
        public void setCreationDate(LocalDateTime creationDate) {
                this.creationDate = creationDate;
        }
        public double getBalance() {
                return balance;
        }
        public void setBalance(double balance) {
                this.balance = balance;
        }
        //Constructor
        public Account(String number, LocalDateTime creationDate, Client client) {
                this.number = number;
                this.creationDate = creationDate;
                this.balance = 0;
                this.client = client;
        }


}



