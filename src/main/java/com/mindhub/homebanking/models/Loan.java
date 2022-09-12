package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
        @Id
        @GeneratedValue (strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(name ="native", strategy = "native")
        private long id;
        private String name;
        private double maxAmount;

        //propiedad Element collection, multivaluada//
        @ElementCollection
        @Column (name = "payments")
        private List<Integer> payments = new ArrayList<> ();

        //Relacion de "tipos" de prestamo a muchos prestamo tomados por clientes//

        @OneToMany(mappedBy= "loan", fetch= FetchType.EAGER)
        private Set<ClientLoan> clientLoans = new HashSet<>();


        //Constructores//
        public Loan() { }

        public Loan(String name, double maxAmount, List<Integer> payments) {
                this.name = name;
                this.maxAmount = maxAmount;
                this.payments = payments;

        }
        @JsonIgnore
        public Set<Client>getClients() {
            return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(Collectors.toSet());
        }
      //Encapsulamiento con getter y setter
        public long getId() { return id; }
        public String getName() {return name;}
        public void setName(String name) {this.name = name;}
        public double getMaxAmount() {return maxAmount;}
        public void setMaxAmount(double maxAmount) {this.maxAmount = maxAmount;}
        public List<Integer> getPayments() {return payments;}
        public void setPayments(List<Integer> payments) {this.payments = payments;}
        public Set<ClientLoan> getClientLoans() {return clientLoans;}

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }
}
