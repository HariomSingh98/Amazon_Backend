package com.Ecommerce.AmazonBackened.RequestDTO;

import com.Ecommerce.AmazonBackened.Enum.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {

    private String name;
    private int price;
    private Category category;
    private int quantity;

    private int seller_id;

}
