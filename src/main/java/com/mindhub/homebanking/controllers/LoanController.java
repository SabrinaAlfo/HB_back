package com.mindhub.homebanking.controllers;



import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        Loan loan = this.loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);
        Account toAccount = this.accountRepository.findByNumber(loanApplicationDTO.getAccountNumber());
        Account fromAccount = this.accountRepository.findByNumber(loanApplicationDTO.getAccountNumber());
        Client client = this.clientRepository.findByEmail(authentication.getName());

        if (loan == null) {
            return new ResponseEntity<>("Seleccione prestamo de la lista", HttpStatus.FORBIDDEN);

        }
        if (loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getPayments() == 0) {
            return new ResponseEntity<>("El monto o las cuotas estan en cero", HttpStatus.FORBIDDEN);
        }
        if (loan.getMaxAmount() < loanApplicationDTO.getAmount()) {
            return new ResponseEntity<>("Excede monto permitdo para prestamo seleccionado", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("Cantidad de cuota invalido", HttpStatus.FORBIDDEN);
        }

        if (!client.getAccounts().contains(toAccount)) {
            return new ResponseEntity<>("Ingrese una cta valida", HttpStatus.FORBIDDEN);
        }
        double interest = loanApplicationDTO.getAmount() * 1.2;
        ClientLoan clientLoan = new ClientLoan(interest, loanApplicationDTO.getPayments(), client, loan);
        clientLoanRepository.save(clientLoan);

        String description = "Tu prestamo ha sido acreditado";
        Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), description, LocalDateTime.now(), toAccount);
        transactionRepository.save(transaction);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
