package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping ("/api")
public class CardController {

@Autowired
private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/api/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardColor cardColor, @RequestParam CardType cardType, Authentication authentication){
        Client client = this.clientRepository.findByEmail(authentication.getName());

        if (cardColor == null || cardType ==null) {
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

        if (client.getCards().size()==3) {
        return new ResponseEntity<>("Tiene disponible maximo 3 tarjetas", HttpStatus.FORBIDDEN);}

//stingBuilder: crear objetos que almacenan cadenas de caracteres que pueden ser modificadas sin necesidad de crear nuevos objetos.//

        cardRepository.save(new Card(cardType, cardColor, stringBuilder(), getRandomNumber(100,999), client));
        return new ResponseEntity<>("SE CREO UNA NUEVA CUENTA", HttpStatus.CREATED);

    }

    private String stringBuilder() {
        return getRandomNumber(1000,9999).toString() + " " +
                getRandomNumber(1000,9999).toString() + " " +
                getRandomNumber(1000, 9999).toString() + " "+
                getRandomNumber(1000,9999).toString();

    }

    private Integer getRandomNumber(int min, int max) { return (int) ((Math.random()* (max -min)) + min);}
    }

