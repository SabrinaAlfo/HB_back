package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return this.clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    //Controller clientLoan//
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @GetMapping("clients/{id}")
    public ClientDTO getClientId(@PathVariable Long id) {
        return new ClientDTO(clientRepository.findById(id).get());

       }
       @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication){
    Client client = this.clientRepository.findByEmail(authentication.getName());
    return new ClientDTO(client);
    }
        @Autowired
        private PasswordEncoder passwordEncoder;

    //metodo que soporta un POST>CREAR (envian un nuevo objeto client/mail/password > nuevo constructor
        @PostMapping("/clients")
        public ResponseEntity<Object> register(
                @RequestParam String firstName, @RequestParam String lastName,
                @RequestParam String email, @RequestParam String password) {

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            }
            if (clientRepository.findByEmail(email) !=  null) {
                return new ResponseEntity<>("Mail ya registrado", HttpStatus.FORBIDDEN);
            }
            clientRepository.save(new Client(firstName,lastName, email, passwordEncoder.encode(password)));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        public String getRandomCardNumber() {return "VIN"+ ((int)((Math.random()*(99999999-10000000))+10000000));}
}






