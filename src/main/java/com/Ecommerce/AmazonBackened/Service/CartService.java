package com.Ecommerce.AmazonBackened.Service;

import com.Ecommerce.AmazonBackened.Exception.CardNotFoundException;
import com.Ecommerce.AmazonBackened.Exception.CustomerNotFoundException;
import com.Ecommerce.AmazonBackened.Exception.InsufficientItemException;
import com.Ecommerce.AmazonBackened.Exception.ProductNotFound;
import com.Ecommerce.AmazonBackened.Model.*;
import com.Ecommerce.AmazonBackened.Repository.CardRepository;

import com.Ecommerce.AmazonBackened.Repository.CustomerRepository;
import com.Ecommerce.AmazonBackened.Repository.ProductRepository;
import com.Ecommerce.AmazonBackened.RequestDTO.CartRequestDTO;
import com.Ecommerce.AmazonBackened.RequestDTO.OrderRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.CartItemResponseDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.OrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class CartService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    OrderService orderService;



    public void addToCart(CartRequestDTO cartRequestDTO) throws Exception {
        //we will have only one item in our cart at any time
        //to add another time we have to empty the cart or checkout
        Customer customer;
        try{
            customer =   customerRepository.findById(cartRequestDTO.getCustomer_id()).get();
        }
        catch (Exception e){
            throw new CustomerNotFoundException("Customer not found");
        }
        Product product;
        try{
            product = productRepository.findById(cartRequestDTO.getProduct_id()).get();
        }
        catch (Exception e){
            throw new ProductNotFound("Invalid product_id");
        }

        //check if required quantity is grater than available
        if(cartRequestDTO.getRequiredQuantity()>product.getQuantity())
            throw new InsufficientItemException("Insufficient Item");


        //get the cart of the given customer
        Cart cart = customer.getCart();

        if(cart.getItemList().size()==1)throw new Exception("Cart is not Empty");
        //update cart attribute
        int newTotal = cart.getCartTotal() + cartRequestDTO.getRequiredQuantity()* product.getPrice();
        cart.setCartTotal(newTotal);

        //make an item object
        Item item = new Item();
        item.setRequiredQuantity(cartRequestDTO.getRequiredQuantity());
        item.setCart(cart);
        item.setProduct(product);

        cart.getItemList().add(item);//add the item in the cart-list

        customerRepository.save(customer);//save the customer and cart will also get saved
    }

    //checkout the cart
    public List<OrderResponseDTO> checkoutCart(int id,String cardNo) throws Exception {
        Customer customer;
        try{
            customer =   customerRepository.findById(id).get();
        }
        catch (Exception e){
            throw new CustomerNotFoundException("Customer not found");
        }

        Card card ;
        try{
            card = cardRepository.findByCardNo(cardNo);
        }
        catch(Exception e ){
            throw new CardNotFoundException("Card not valid");
        }

       List<OrderResponseDTO> orderResponseDTOS  = new ArrayList<>();
        //we will call place order on all the item of a cart one by one
        //we need to create a orderRequestDTO to place a order

        Cart cart = customer.getCart();//get the cart of the customer


        List<Item> itemList = cart.getItemList();

        if(itemList.size()==0)throw new Exception("Cart is empty");

        for(Item item : itemList){
            OrderRequestDTO orderRequestDTO = new OrderRequestDTO();//create a orderRequestDto
            orderRequestDTO.setCustomerId(customer.getId());
            orderRequestDTO.setProductId(item.getProduct().getId());
            orderRequestDTO.setRequiredQuantity(item.getRequiredQuantity());
            orderRequestDTO.setCardNo(cardNo);

            OrderResponseDTO orderResponseDTO = orderService.directBuyItem(orderRequestDTO);
            //call the direct buy function for every item in the cart

            orderResponseDTOS.add(orderResponseDTO);
        }

        //update the cart
        emptyCart(id);

        return orderResponseDTOS;


    }

    public void emptyCart(int id) throws CustomerNotFoundException {
        Customer customer;
        try{
            customer =   customerRepository.findById(id).get();
        }
        catch (Exception e){
            throw new CustomerNotFoundException("Customer not found");
        }

        Cart cart = customer.getCart();

        cart.setItemList(new ArrayList<>());
        cart.setCartTotal(0);

        customerRepository.save(customer);
    }

    public List<CartItemResponseDTO> viewCartItem(int id) throws Exception {
        Customer customer;
        try{
            customer =   customerRepository.findById(id).get();
        }
        catch (Exception e){
            throw new CustomerNotFoundException("Customer not found");
        }

        List<CartItemResponseDTO> cartItemResponseDTOS = new ArrayList<>();
        Cart cart = customer.getCart();

        List<Item> itemList = cart.getItemList();

        if(itemList.size()==0)throw new Exception("Cart is empty");

        for(Item item : itemList){
            CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();
            cartItemResponseDTO.setProductName(item.getProduct().getName());
            cartItemResponseDTO.setPrice(item.getProduct().getPrice());

            cartItemResponseDTOS.add(cartItemResponseDTO);
        }


        return cartItemResponseDTOS;
    }

}
