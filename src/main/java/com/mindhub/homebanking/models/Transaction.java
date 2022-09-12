package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

//Entity crea tabla con columnas segun caracteristicas del objeto//
@Entity
public class Transaction {
    @Id //llave univoca en la BD//
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") //genera valor automático//
    @GenericGenerator(name = "native", strategy = "native")  //forma en que genera valor//
    //atributos,caracteristicas//
    private Long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;

    //varias trx de un IDclient
    //@ManyToOne(fetch= FetchType.EAGER)
    //@JoinColumn(name= "client_id")
    //private Client client;

    //varias trx de una IDCTA
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name= "account_id")
    private Account account;


    // contructores, primero el vacío//
    public Transaction() {
    }

    public Transaction(TransactionType type, double amount, String description, LocalDateTime date, Account account) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.account = account;
    }
    //Get y set: Metodos (para ID solo GET)//
    public Long getId() {
        return id;
    }
    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public Account getAccount() {
        return account;
    }

    //public Client getClient() {return client;}
    //public void setClient(Client client) {this.client = client;}

    public void setAccount(Account account) {
        this.account = account;
    }


}
