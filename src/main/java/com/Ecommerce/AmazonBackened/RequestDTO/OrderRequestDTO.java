package com.Ecommerce.AmazonBackened.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderRequestDTO {//all the attribute required to place an order
    private int customerId;
    private int productId;
    private int requiredQuantity;
    private String cardNo;

}
