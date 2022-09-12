package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    //un cliente varias ctas
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

     //un cliente varios prestamos
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    // Un cliente varias TC/TD
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();


    public Client() { }
    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;}


    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    //getter y setter Ctas
    public Set<Account> getAccounts() {
        return accounts;
    }
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);

    }

    //encapsulamiento getter y setter ClientLoan
    public Set<ClientLoan> getClientLoans() {return clientLoans;}
    public void setClientLoans(Set<ClientLoan> clientLoans) {this.clientLoans = clientLoans;}
    public void addClientLoan(ClientLoan clientLoan) {
        this.clientLoans.add(clientLoan);
        clientLoan.setClient(this); }

    //Getter y setter de Cards
    public Set<Card> getCards() {return cards;}
    public void setCards(Set<Card> cards) {this.cards = cards;}

    //Getter y setter de password
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}


}