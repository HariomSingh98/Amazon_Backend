package com.Ecommerce.AmazonBackened.Controller;

import com.Ecommerce.AmazonBackened.RequestDTO.SellerRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.SellerResponseDTO;
import com.Ecommerce.AmazonBackened.Service.SellerService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService service;

    @PostMapping("/add")
    public SellerResponseDTO addSeller(@RequestBody SellerRequestDTO sellerRequestDTO){
        return service.addSeller(sellerRequestDTO);
    }

    //get all seller
    @GetMapping("/all_sellers")
    public List<SellerResponseDTO> findAllSeller(){

        return service.findAllSellers();
    }

    //get a seller by pan card
    @GetMapping("/pan_card")
    public SellerResponseDTO SellerByPan(@RequestParam String panNo){
        return service.sellerByPan(panNo);
    }

    //find sellers of particular age
    @GetMapping("/sellers_by_age/{age}")
    public List<SellerResponseDTO> sellersByAge(@PathVariable int age){
        return service.sellersByAge(age);
    }
}
