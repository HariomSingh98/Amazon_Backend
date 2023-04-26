package com.Ecommerce.AmazonBackened.ResponseDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CardResponseDTO {

    private String name;

    private List<CardDetailDTO> cardDetailDTOList;//list of call cards hold by a customer

}
