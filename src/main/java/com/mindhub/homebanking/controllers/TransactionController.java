package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping ("/api")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;


    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(@RequestParam String accountFromNumber,
                                                    @RequestParam String accountToNumber,
                                                    @RequestParam double amount,
                                                    @RequestParam String description, Authentication authentication){

        Client client = this.clientRepository.findByEmail(authentication.getName());
        Account fromAccount= this.accountRepository.findByClientAndNumber(client, accountFromNumber);
        Account toAccount = this.accountRepository.findByNumber(accountToNumber);


        if(amount <1 || description.isEmpty() || accountFromNumber.isEmpty() || accountToNumber.isEmpty()) {
            return new ResponseEntity<>("Todos los campos deben estar completos", HttpStatus.FORBIDDEN);
          }

        if(accountToNumber == accountFromNumber) {
        return new ResponseEntity<>("Error. La cuenta destino es igual a la de origen",HttpStatus.FORBIDDEN);
          }

        if (accountToNumber== null) {
            return new ResponseEntity<>("La cuenta destino no existe", HttpStatus.FORBIDDEN);
          }

        if (accountFromNumber== null) {
            return new ResponseEntity<>("La cuenta origen no existe", HttpStatus.FORBIDDEN);
          }

        /*if(accountFromNumber.getBalance() < amount ){
            return new ResponseEntity<>( "Fondos Insuficientes", HttpStatus.FORBIDDEN);
        }*/
        double balance = accountRepository.findByClientAndNumber(client,accountFromNumber).getBalance();
        if (balance < amount) {
            return new ResponseEntity<>( "Fondos Insuficientes", HttpStatus.FORBIDDEN);
        }

        if (accountRepository.findByClientAndNumber(client, accountFromNumber)==null)
            return new ResponseEntity<>("Verifique la cuenta Origen",HttpStatus.FORBIDDEN);

        transactionRepository.save (new Transaction(TransactionType.CREDIT, amount, accountToNumber + description, LocalDateTime.now(),toAccount));
        transactionRepository.save (new Transaction(TransactionType.DEBIT, -amount, accountFromNumber + description, LocalDateTime.now(),fromAccount));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}


