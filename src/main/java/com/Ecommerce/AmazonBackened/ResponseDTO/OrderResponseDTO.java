package com.Ecommerce.AmazonBackened.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderResponseDTO
{
    private String productName;
    private int itemPrice;
    private int quantityOrdered;
    private int totalCost;
    @CreationTimestamp
    private Date orderDate;
    private int deliveryCharge;
    private String cardUsed;

}
