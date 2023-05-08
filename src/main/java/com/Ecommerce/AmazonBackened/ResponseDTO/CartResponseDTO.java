package com.Ecommerce.AmazonBackened.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {//use when we add  item to cart

    private int cartTotal;
    private int numberOfItem;
    private String customerName;

    private List<ItemResponseDTO> itemList;//list of items in the cart


}
