package com.Ecommerce.AmazonBackened.ResponseDTO;

import com.Ecommerce.AmazonBackened.Enum.Category;

import com.Ecommerce.AmazonBackened.Enum.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponseDTO {//class which picks what to show the user about an item
    private String name;
    private int price;

    private Category category;
    private ProductStatus status;
}
