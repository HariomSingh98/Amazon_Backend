package com.Ecommerce.AmazonBackened.converter;

import com.Ecommerce.AmazonBackened.Enum.ProductStatus;
import com.Ecommerce.AmazonBackened.Model.Product;
import com.Ecommerce.AmazonBackened.RequestDTO.ProductRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.ProductResponseDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductConvertor {

    public static ProductResponseDTO productToDto(Product product){
         return ProductResponseDTO.builder()
                 .name(product.getName())
                 .price(product.getPrice())
                 .status(product.getStatus())
                 .quantity(product.getQuantity())
                 .build();
    }

    public static Product dtoToProduct(ProductRequestDTO productRequestDTO){
        return Product.builder()
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .quantity(productRequestDTO.getQuantity())
                .category(productRequestDTO.getCategory())
                .status(ProductStatus.AVAILABLE)
                .build();
    }
}
