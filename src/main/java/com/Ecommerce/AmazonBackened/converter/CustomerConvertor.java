package com.Ecommerce.AmazonBackened.converter;

import com.Ecommerce.AmazonBackened.Model.Customer;
import com.Ecommerce.AmazonBackened.RequestDTO.CustomerRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.CustomerResponseDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerConvertor {

    public static CustomerResponseDTO customerToDto(Customer customer){
        return CustomerResponseDTO.builder()
                .name(customer.getName())
                .mobNo(customer.getMobNo())
                .build();

    }
    public  static Customer DtoToCustomer(CustomerRequestDTO customerRequestDTO){
        return   Customer.builder()
                .name(customerRequestDTO.getName())
                .mobNo(customerRequestDTO.getMobNo())
                .age(customerRequestDTO.getAge())
                .email(customerRequestDTO.getEmail())
                .build();

    }
}
