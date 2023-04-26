package com.Ecommerce.AmazonBackened.converter;

import com.Ecommerce.AmazonBackened.Model.Seller;
import com.Ecommerce.AmazonBackened.RequestDTO.SellerRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.SellerResponseDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SellerConvertor {

    public static Seller  dtoToSeller(SellerRequestDTO sellerRequestDTO){
        //create new seller object with new keyword
//        Seller newSeller = new Seller();
//        newSeller.setName(sellerRequestDTO.getName());
//        newSeller.setEmail(sellerRequestDTO.getEmail());
//        newSeller.setMobNo(sellerRequestDTO.getMobNo());
//        newSeller.setPanNo(sellerRequestDTO.getPanNo());

        //create the object of seller using @Builder
        return   Seller.builder()
                .name(sellerRequestDTO.getName())
                .email(sellerRequestDTO.getEmail())
                .mobNo(sellerRequestDTO.getMobNo())
                .panNo(sellerRequestDTO.getPanNo())
                .age(sellerRequestDTO.getAge())
                .build();
    }
    public static SellerResponseDTO sellerToDto(Seller seller){
          return SellerResponseDTO.builder()
                  .name(seller.getName())
                  .age(seller.getAge())
                  .build();
    }
}
