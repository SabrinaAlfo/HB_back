package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository,
									   LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123"));
			Client client2 = new Client("Gabi", "Sol", "gabi@hotmail.com",passwordEncoder.encode("123"));
			Client client3 = new Client("Dani", "Morel", "dani@hotmail.com",passwordEncoder.encode("123"));
			Client client4 = new Client("Nat", "Luna", "nat@admin.com",passwordEncoder.encode("123"));
			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);
			clientRepository.save(client4);

			//Account(String number, LocalDateTime creationDate, Client client) {
			Account account1 = new Account("VIN001", LocalDateTime.now(), client1);
			Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1), client1);
			Account account3 = new Account("VIN333", LocalDateTime.now().plusDays(1), client2);
			Account account4 = new Account("VIN111", LocalDateTime.now().plusDays(1), client2);
			Account account5 = new Account("VIN222", LocalDateTime.now(),client2);
			Account account6 = new Account("VIN999", LocalDateTime.now(),client3);
			Account account7 = new Account("VIN888", LocalDateTime.now(), client4);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);
			accountRepository.save(account5);
			accountRepository.save(account6);
			accountRepository.save(account7);


			Transaction transaction1 = new Transaction(TransactionType.CREDIT, 2000, "transferencia recibida", LocalDateTime.now(), account1);
			Transaction transaction2 = new Transaction(TransactionType.DEBIT, -4000, "Compra tienda xx", LocalDateTime.now(), account1);
			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 1000, "transferencia recibida", LocalDateTime.now(), account2);
			Transaction transaction4 = new Transaction(TransactionType.DEBIT, -200, "Compra tienda xy", LocalDateTime.now(), account2);
			Transaction transaction5 = new Transaction(TransactionType.CREDIT, 8000, "transferencia recibida", LocalDateTime.now(), account3);
			Transaction transaction6 = new Transaction(TransactionType.DEBIT, -2000, "Compra tienda xz", LocalDateTime.now(), account3);
			Transaction transaction7 = new Transaction(TransactionType.CREDIT, 700, "transferencia recibida", LocalDateTime.now(), account4);
			Transaction transaction8 = new Transaction(TransactionType.DEBIT, -2000, "Compra tienda xi", LocalDateTime.now(), account4);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
			transactionRepository.save(transaction7);
			transactionRepository.save(transaction8);


			//apunte:Si desea rellenar previamente un ArrayList y agregarlo después, use:
			//List<String> strings = new ArrayList<>(List.of("foo", "bar"));
			//strings.add("baz");//

			List<Integer> listloan1 = new ArrayList<>(List.of(12, 24, 36, 48, 60));
			Loan loan1 = new Loan("Hipotecario", 500.000, listloan1);

			List<Integer> listloan2 = new ArrayList<>(List.of(6, 12, 24));
			Loan loan2 = new Loan("Personal", 100.000, listloan2);

			List<Integer> listloan3 = new ArrayList<>(List.of(6, 12, 24, 36));
			Loan loan3 = new Loan("Automotriz", 300.000, listloan3);


			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);


			//ClientLoan(double amount, int payments, Client client, Loan loan)
			ClientLoan clientLoan1 = new ClientLoan(350000, 24, client1, loan3);
			ClientLoan clientLoan2 = new ClientLoan(500000, 12, client2, loan1);
			ClientLoan clientLoan3 = new ClientLoan(50000, 6, client1, loan2);
			ClientLoan clientLoan4 = new ClientLoan(260000, 36, client3, loan3);
			ClientLoan clientLoan5 = new ClientLoan(111000, 36, client4, loan3);
			ClientLoan clientLoan6 = new ClientLoan(500000, 60, client3, loan1);
			ClientLoan clientLoan7 = new ClientLoan(111000, 24, client4, loan2);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);
			clientLoanRepository.save(clientLoan5);
			clientLoanRepository.save(clientLoan6);
			clientLoanRepository.save(clientLoan7);




			//public Card(CardType type, CardColor color, String number, int cvv, Client client)
			Card card1 = new Card(CardType.CREDIT, CardColor.TITANIUM , "1234 5678 9012 3456",012, client1);
			Card card2 = new Card(CardType.DEBIT, CardColor.GOLD, "1234 5678 9012 5555",987, client1);
			Card card3 = new Card(CardType.CREDIT, CardColor.SILVER , "1111 2222 3333 4444",123,client2);
			Card card4 = new Card(CardType.DEBIT, CardColor.SILVER, "1234 5678 9012 5555",987, client2);
			Card card5 = new Card(CardType.CREDIT, CardColor.GOLD , "1111 2222 3333 4444",123,client3);
			Card card6 = new Card(CardType.DEBIT, CardColor.TITANIUM, "1234 5678 9012 5555",987,client3);
			Card card7 = new Card(CardType.CREDIT, CardColor.SILVER , "1111 2222 3333 4444",123,client4);
			Card card8 = new Card(CardType.DEBIT, CardColor.GOLD, "1234 5678 9012 5555",987,client4);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
			cardRepository.save(card4);
			cardRepository.save(card5);
			cardRepository.save(card6);
			cardRepository.save(card7);
			cardRepository.save(card8);




		};
	}


}
