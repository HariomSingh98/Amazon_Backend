package com.Ecommerce.AmazonBackened.Controller;

import com.Ecommerce.AmazonBackened.Enum.Category;
import com.Ecommerce.AmazonBackened.Exception.SellerNotFoundException;
import com.Ecommerce.AmazonBackened.RequestDTO.ProductRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.ProductResponseDTO;
import com.Ecommerce.AmazonBackened.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    ProductService service;
    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody  ProductRequestDTO productRequestDTO){
        ProductResponseDTO productResponseDTO ;
        try{
            productResponseDTO= service.addProduct(productRequestDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(productResponseDTO,HttpStatus.ACCEPTED);

    }

    @GetMapping("/category/{category}")
    public ResponseEntity getProductByCategory(@PathVariable  Category category){
        List<ProductResponseDTO>  productResponseDTOList;
        try{
            productResponseDTOList = service.getProductByCategory(category);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(productResponseDTOList,HttpStatus.ACCEPTED);
    }
}
