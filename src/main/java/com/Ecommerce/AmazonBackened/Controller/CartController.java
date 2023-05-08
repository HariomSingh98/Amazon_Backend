package com.Ecommerce.AmazonBackened.Controller;

import com.Ecommerce.AmazonBackened.RequestDTO.CartRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.CartResponseDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.OrderResponseDTO;
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
    @PostMapping("/add_item")//output the cart response dto
    public ResponseEntity addToCart(@RequestBody CartRequestDTO cartRequestDTO){
        CartResponseDTO cartResponseDTO;
        try{
            cartResponseDTO = cartService.addToCart(cartRequestDTO);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(cartResponseDTO,HttpStatus.ACCEPTED);
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

        @PostMapping("/clear/{customer_id}")
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
        CartResponseDTO cartResponseDTO;
        try{
            cartResponseDTO =  cartService.viewCart(customer_id);
        }
        catch(Exception e ){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cartResponseDTO,HttpStatus.ACCEPTED);
    }


}
