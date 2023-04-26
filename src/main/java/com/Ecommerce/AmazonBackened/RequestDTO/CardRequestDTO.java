package com.Ecommerce.AmazonBackened.RequestDTO;

import com.Ecommerce.AmazonBackened.Enum.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequestDTO {
    private String cardNo;
    private int cvv;
    private CardType type;

    private int customer_id;
}
