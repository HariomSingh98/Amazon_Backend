package com.Ecommerce.AmazonBackened.Service;

import com.Ecommerce.AmazonBackened.Exception.CardNotFoundException;
import com.Ecommerce.AmazonBackened.Exception.CustomerNotFoundException;
import com.Ecommerce.AmazonBackened.Model.Card;
import com.Ecommerce.AmazonBackened.Model.Customer;
import com.Ecommerce.AmazonBackened.Repository.CardRepository;
import com.Ecommerce.AmazonBackened.Repository.CustomerRepository;
import com.Ecommerce.AmazonBackened.RequestDTO.CardRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.CardDetailDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.CardResponseDTO;
import com.Ecommerce.AmazonBackened.converter.CardConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CardRepository cardRepository;
    //add a card to a customer
    public CardResponseDTO addCardToCustomer(CardRequestDTO cardRequestDTO) throws CustomerNotFoundException {
        Customer customer;
        try{
            customer = customerRepository.findById(cardRequestDTO.getCustomer_id()).get();
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Invalid Customer_id");
        }
        //make new card object and set it attribute
        Card newCard ;
        newCard = CardConvertor.DtoToCard(cardRequestDTO);
        newCard.setCustomer(customer);

        //add the card to cardlist of customer
        customer.getCardList().add(newCard);


        //save the customer
        customerRepository.save(customer);


        //make the response object
        CardResponseDTO cardResponseDTO = new CardResponseDTO() ;
        List<CardDetailDTO> list = new ArrayList<>();

        //iterate over all cards of a customer
        for(Card c : customer.getCardList()){
            CardDetailDTO cardDetailDTO = new CardDetailDTO();
            cardDetailDTO = CardConvertor.cardToDetailDto(c);
            list.add(cardDetailDTO);//add the details dto of card to list
        }
        //set the attribute of  cardResponse
        cardResponseDTO.setName(customer.getName());
        cardResponseDTO.setCardDetailDTOList(list);


        return cardResponseDTO;

    }
    //delete a  particular card
    public void deleteCard(String cardNo) throws CardNotFoundException {
        Card card =null;
        Customer customer= null;//find the customer corresponding to given card
        List<Card> list = cardRepository.findAll();
        for(Card c : list){
            if(c.getCardNo().equals(cardNo)){
                card= c;
                customer = c.getCustomer();
                break;
            }
        }
        if(card==null)throw new CardNotFoundException("CardNo is not valid");

        customer.getCardList().remove(card);

        int id = card.getId();

        cardRepository.deleteById(id);


    }

    //delete all card for a customer
    public void deleteAllCardByCustomer(int id) throws CustomerNotFoundException{
        Customer customer;
        try{
            customer = customerRepository.findById(id).get();
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Invalid Customer_id");
        }

        List<Card> cards = cardRepository.findAll();

        for(Card c : cards){
            if(c.getCustomer().equals(customer)){
                cardRepository.deleteById(c.getId());
            }
        }

        customer.setCardList(new ArrayList<>());


    }
    //get all card of particular customer
    public CardResponseDTO getCustomerCards(int id) throws CustomerNotFoundException {
        Customer customer;
        try{
            customer = customerRepository.findById(id).get();
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Invalid Customer_id");
        }

        CardResponseDTO cardResponseDTO = new CardResponseDTO();
        cardResponseDTO.setName(customer.getName());

        List<CardDetailDTO> list = new ArrayList<>();

        for(Card c : customer.getCardList()){
            CardDetailDTO cardDetailDTO = CardConvertor.cardToDetailDto(c);
            list.add(cardDetailDTO);
        }

        cardResponseDTO.setCardDetailDTOList(list);

        return cardResponseDTO;
    }
}
