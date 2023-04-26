package com.Ecommerce.AmazonBackened.Controller;

import com.Ecommerce.AmazonBackened.RequestDTO.CartRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.CartItemResponseDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.OrderResponseDTO;
import com.Ecommerce.AmazonBackened.Service.CardService;
import com.Ecommerce.AmazonBackened.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    //add an item to the cart
    @PostMapping("/add_item")
    public ResponseEntity addToCart(@RequestBody CartRequestDTO cartRequestDTO){
        try{
            cartService.addToCart(cartRequestDTO);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully added to the cart",HttpStatus.ACCEPTED);
    }
    @PostMapping("/checkout/{customer_id}")//card will be the 1st one
        public ResponseEntity checkoutCart(@PathVariable int customer_id, @RequestParam String cardNo){
            List<OrderResponseDTO> orderResponseDTOS;
        try{
              orderResponseDTOS = cartService.checkoutCart(customer_id,cardNo);
          }
          catch(Exception e ){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
          }

        return new ResponseEntity<>(orderResponseDTOS,HttpStatus.ACCEPTED);
        }

        @PutMapping("/clear/{customer_id}")
        public ResponseEntity emptyCart(@PathVariable int customer_id){
            try{
                cartService.emptyCart(customer_id);
            }
            catch(Exception e ){
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>("Successfully Empty the cart",HttpStatus.ACCEPTED);
        }

    @GetMapping("/view/{customer_id}")
    public ResponseEntity viewCartItem(@PathVariable int customer_id){
        List<CartItemResponseDTO> cartItemResponseDTOs ;
        try{
            cartItemResponseDTOs = cartService.viewCartItem(customer_id);
        }
        catch(Exception e ){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cartItemResponseDTOs,HttpStatus.ACCEPTED);
    }


}
