package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/account")
    public Set<AccountDTO> getAccount() {
        return this.accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    //muestra en la ruta de pantalla por ID cuenta//
    @GetMapping("/accounts/{id}")
        public AccountDTO getAccounts(@PathVariable Long id, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findById(id).orElse(null);
        if (!client.getAccounts().contains(account)){
        }
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
    Client client = this.clientRepository.findByEmail(authentication.getName());
    return client.getAccounts().stream().map(AccountDTO::new).collect(toList());
    }

    /*@RequestMapping(path = "/clients/current/accounts",method= RequestMethod.GET)
    public List<AccountDTO> getCurrentAccounts(Authentication authentication){
        Client client = this.clientRepository.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect((toList()));
    }*/

    //CREA UNA NUEVA CTA (post) CON LIMITE DE 3 CTAS
    private ClientRepository clientRepository;

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client client = this.clientRepository.findByEmail(authentication.getName());
    //size trae las ctas getaccount
        if (client == null){
            return new ResponseEntity<>("no autorizado",HttpStatus.UNAUTHORIZED);
        }
        if (client.getAccounts().size() == 3) {
            return new ResponseEntity<>("ALCANZO LAS 3 CUENTAS PERMITIDAS", HttpStatus.FORBIDDEN);
        }

        //randmom: generador de num
        String accountNumber = "VIN" + ((int) ((Math.random() * (99999999 - 10000000)) + 10000000));
        Account account = new Account(accountNumber, LocalDateTime.now(), client);
        accountRepository.save(account);

        return new ResponseEntity<>("SE CREO UNA NUEVA CUENTA", HttpStatus.CREATED);
    }

}


