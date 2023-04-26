package com.Ecommerce.AmazonBackened.Controller;

import com.Ecommerce.AmazonBackened.ResponseDTO.ItemResponseDTO;
import com.Ecommerce.AmazonBackened.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/view/{product_Id}")//to view a product
    public ResponseEntity viewItem(@PathVariable int product_Id){
        ItemResponseDTO itemResponseDTO;
        try{
            itemResponseDTO = itemService.viewItem(product_Id);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(itemResponseDTO,HttpStatus.ACCEPTED);
    }

}
