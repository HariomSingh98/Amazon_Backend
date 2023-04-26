package com.Ecommerce.AmazonBackened.Controller;

import com.Ecommerce.AmazonBackened.RequestDTO.OrderRequestDTO;

import com.Ecommerce.AmazonBackened.ResponseDTO.OrderResponseDTO;
import com.Ecommerce.AmazonBackened.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class OrderController {

    @Autowired
    OrderService orderService;




    //directly place the order after viewing
    @PostMapping("/order/place_a_order")
    public  ResponseEntity directBuyItem(@RequestBody  OrderRequestDTO orderRequestDTO){
        OrderResponseDTO orderResponseDTO;
        try{
            orderResponseDTO = orderService.directBuyItem(orderRequestDTO);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(orderResponseDTO,HttpStatus.ACCEPTED);
    }




}
