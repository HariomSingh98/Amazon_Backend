package com.Ecommerce.AmazonBackened.Service;

import com.Ecommerce.AmazonBackened.Enum.ProductStatus;
import com.Ecommerce.AmazonBackened.Exception.CardNotFoundException;
import com.Ecommerce.AmazonBackened.Exception.CustomerNotFoundException;
import com.Ecommerce.AmazonBackened.Exception.InsufficientItemException;
import com.Ecommerce.AmazonBackened.Exception.ProductNotFound;
import com.Ecommerce.AmazonBackened.Model.*;
import com.Ecommerce.AmazonBackened.Repository.CardRepository;

import com.Ecommerce.AmazonBackened.Repository.CartRepository;
import com.Ecommerce.AmazonBackened.Repository.CustomerRepository;
import com.Ecommerce.AmazonBackened.Repository.ProductRepository;
import com.Ecommerce.AmazonBackened.RequestDTO.CartRequestDTO;
import com.Ecommerce.AmazonBackened.RequestDTO.OrderRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.CartResponseDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.ItemResponseDTO;
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

    @Autowired
    CartRepository cartRepository;





    public CartResponseDTO addToCart(CartRequestDTO cartRequestDTO) throws Exception {

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

        //check if required quantity is grater than available OR it is not available
        if(cartRequestDTO.getRequiredQuantity()>product.getQuantity() || product.getStatus().equals(ProductStatus.OUT_OF_STOCK))
            throw new InsufficientItemException("Insufficient Item");



        //get the cart of the given customer
        Cart cart = customer.getCart();


        //update cart attribute
        int newTotal = cart.getCartTotal() + cartRequestDTO.getRequiredQuantity()* product.getPrice();
        cart.setCartTotal(newTotal);

        //make an item object
        Item item = new Item();
        item.setRequiredQuantity(cartRequestDTO.getRequiredQuantity());
        item.setCart(cart);
        item.setProduct(product);

        cart.getItemList().add(item);//add the item in the cart-list
        cart.setNumberOfItems(cart.getNumberOfItems()+1);//increment the item counter

        customerRepository.save(customer);//save the customer and cart will also get saved


        //create the cart Response object
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setCartTotal(newTotal);
        cartResponseDTO.setNumberOfItem(cart.getNumberOfItems());
        cartResponseDTO.setCustomerName(customer.getName());
        List<ItemResponseDTO> itemResponseDTOS = new ArrayList<>();
        for(Item item1 : cart.getItemList()){
            ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
            itemResponseDTO.setName(item1.getProduct().getName());
            itemResponseDTO.setPrice(item1.getProduct().getPrice());
            itemResponseDTO.setCategory(item1.getProduct().getCategory());
            itemResponseDTO.setStatus(item1.getProduct().getStatus());

            itemResponseDTOS.add(itemResponseDTO);
        }
        cartResponseDTO.setItemList(itemResponseDTOS);

        return cartResponseDTO;
    }

    //checkout the cart
    public List<OrderResponseDTO> checkoutCart(int id,String cardNo) throws Exception {//need some update
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
            throw new CardNotFoundException("Card is not valid");
        }

        if(card.getCustomer()!=customer)throw  new Exception("Card is not valid");

        Cart cart = customer.getCart();//get the cart of the customer
        if(cart.getItemList().size()==0)throw  new Exception("Cart is Empty");


        List<OrderResponseDTO> orderResponseDTOS  = new ArrayList<>();
        //we will call place order on all the item of a cart one by one
        //we need to create a orderRequestDTO to place a order




        List<Item> itemList = cart.getItemList();


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

        //reset  the cart
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

        List<Item> itemList = cart.getItemList();
        for(Item i : itemList){//remove the cart mapped to the item  since it is a bidirectional mapping
            i.setCart(null);
        }
        cart.setCartTotal(0);
        cart.setNumberOfItems(0);

        cart.setItemList(new ArrayList<>());

        cartRepository.save(cart);



    }

    public CartResponseDTO viewCart(int id) throws Exception {
        Customer customer;
        try{
            customer =   customerRepository.findById(id).get();
        }
        catch (Exception e){
            throw new CustomerNotFoundException("Customer not found");
        }

        //get the cart of the given customer
        Cart cart = customer.getCart();
        List<Item> itemList = cart.getItemList();
        if(itemList.isEmpty())throw new Exception("Cart is Empty");

        //create the cart Response object
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setCartTotal(cart.getCartTotal());
        cartResponseDTO.setNumberOfItem(cart.getNumberOfItems());
        cartResponseDTO.setCustomerName(customer.getName());
        List<ItemResponseDTO> itemResponseDTOS = new ArrayList<>();


        for(Item item1 :itemList ){
            ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
            itemResponseDTO.setName(item1.getProduct().getName());
            itemResponseDTO.setPrice(item1.getProduct().getPrice());
            itemResponseDTO.setCategory(item1.getProduct().getCategory());
            itemResponseDTO.setStatus(item1.getProduct().getStatus());

            itemResponseDTOS.add(itemResponseDTO);
        }
        cartResponseDTO.setItemList(itemResponseDTOS);

        return cartResponseDTO;
    }

}
