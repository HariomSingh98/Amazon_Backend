package com.Ecommerce.AmazonBackened.RequestDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SellerRequestDTO {

    private String name;


    private String email;

    private String mobNo;

    private String panNo;

    private int age;
}
