package com.Ecommerce.AmazonBackened.converter;

import com.Ecommerce.AmazonBackened.Model.Card;
import com.Ecommerce.AmazonBackened.RequestDTO.CardRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.CardDetailDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CardConvertor {
    public static Card DtoToCard(CardRequestDTO cardRequestDTO){
        return Card.builder()
                .cardNo(cardRequestDTO.getCardNo())
                .cvv(cardRequestDTO.getCvv())
                .cardType(cardRequestDTO.getType())
                .build();
    }
    public static CardDetailDTO cardToDetailDto(Card card){
        return CardDetailDTO.builder()
                .cardNo(card.getCardNo())
                .cardType(card.getCardType())
                .build();
    }
}
