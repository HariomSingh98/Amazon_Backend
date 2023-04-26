package com.Ecommerce.AmazonBackened.converter;

import com.Ecommerce.AmazonBackened.Model.Item;
import com.Ecommerce.AmazonBackened.Model.Product;
import com.Ecommerce.AmazonBackened.ResponseDTO.ItemResponseDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemConvertor {

    public static ItemResponseDTO productToDto(Product p){
        return ItemResponseDTO.builder()
                .name(p.getName())
                .price(p.getPrice())
                .status(p.getStatus())
                .category(p.getCategory())
                .build();
    }

    public static Item newItemFromProduct(Product p){
        return Item.builder()
                .requiredQuantity(0)
                .product(p)
                .build();
    }
}
