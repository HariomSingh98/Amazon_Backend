package com.Ecommerce.AmazonBackened.ResponseDTO;

import com.Ecommerce.AmazonBackened.Enum.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductResponseDTO {
    private String  name;
    private int price;
    private int quantity;
    private ProductStatus status;
}
