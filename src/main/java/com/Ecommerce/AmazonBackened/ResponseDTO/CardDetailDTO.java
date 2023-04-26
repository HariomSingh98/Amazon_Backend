package com.Ecommerce.AmazonBackened.ResponseDTO;

import com.Ecommerce.AmazonBackened.Enum.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDetailDTO {
    private String cardNo;
    private CardType cardType;
}
