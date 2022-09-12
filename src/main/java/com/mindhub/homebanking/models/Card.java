package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDateTime fromDate;
    private LocalDateTime thruDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name= "client_id")
    private Client client;


    //contructor vacío
    public Card() { }
     //contructores

    public Card(CardType type, CardColor color, String number, int cvv, Client client) {
        this.cardHolder = client.getLastName().toUpperCase()+ " " + client.getFirstName().toUpperCase();
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = LocalDateTime.now();
        this.thruDate = fromDate.plusYears(5);
        this.client = client;

    }


    //getters y setters

    public long getId() {return id;}
    public String getCardHolder() { return cardHolder;}
    public void setCardHolder(String cardHolder) {this.cardHolder = cardHolder;}
    public CardType getType() { return type;}
    public void setType(CardType type) {this.type = type;}
    public CardColor getColor() {return color;}
    public void setColor(CardColor color) {this.color = color;}
    public String getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}
    public int getCvv() {return cvv;}
    public void setCvv(int cvv) {this.cvv = cvv;}

    public void setCard(Client client) { }

    public void setFromDate(LocalDateTime fromDate) {this.fromDate = fromDate;}
    public void setThruDate(LocalDateTime thruDate) {this.thruDate = thruDate;}

    public LocalDateTime getFromDate() {return fromDate;}
    public LocalDateTime getThruDate() {return thruDate;}
}
