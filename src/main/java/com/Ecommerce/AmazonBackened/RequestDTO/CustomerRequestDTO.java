package com.Ecommerce.AmazonBackened.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerRequestDTO {
    private String name ;
    private int age;
    private String email;
    private String mobNo;

}
