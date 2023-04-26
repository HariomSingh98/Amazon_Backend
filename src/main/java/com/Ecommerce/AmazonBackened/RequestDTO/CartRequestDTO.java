package com.Ecommerce.AmazonBackened.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequestDTO {//attribute needed to add an item to the cart
    private int customer_id;
    private int product_id;
    private int requiredQuantity;
}
