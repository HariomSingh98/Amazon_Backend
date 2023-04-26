package com.Ecommerce.AmazonBackened.Controller;

import com.Ecommerce.AmazonBackened.Exception.CustomerNotFoundException;

import com.Ecommerce.AmazonBackened.RequestDTO.CardRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.CardResponseDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.CustomerResponseDTO;
import com.Ecommerce.AmazonBackened.Service.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("card")
public class CardController {
    @Autowired
    CardService service;

    @PostMapping("/add")
    public ResponseEntity addCardToCustomer(@RequestBody CardRequestDTO cardRequestDTO) throws CustomerNotFoundException {
        CardResponseDTO cardResponseDTO;
        try{
            cardResponseDTO = service.addCardToCustomer(cardRequestDTO);
        }
        catch(Exception e ){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(cardResponseDTO,HttpStatus.ACCEPTED);
    }
    //delete a card with card no
    @DeleteMapping("/delete")
    public ResponseEntity deleteCard(@RequestParam String cardNo){
       try{
           service.deleteCard(cardNo);
       }
       catch(Exception e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
       }

       return new ResponseEntity<>("Successfully Deleted the card",HttpStatus.ACCEPTED);
    }
    //delete all card for a customer
    @DeleteMapping("/deleteAllCards/{id}")
    public ResponseEntity deleteAllCardByCustomer(@PathVariable int id ){
        try{
            service.deleteAllCardByCustomer(id);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
       String text = "Successfully deleted all cards of customer with id "+ id;
        return new ResponseEntity<>(text,HttpStatus.ACCEPTED);
    }
    //get all cards for a customer
    @GetMapping("/{id}")
    public ResponseEntity getCustomerCards(@PathVariable int id){
        CardResponseDTO cardResponseDTO;
        try{
           cardResponseDTO =  service.getCustomerCards(id);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cardResponseDTO,HttpStatus.ACCEPTED);
    }


}
