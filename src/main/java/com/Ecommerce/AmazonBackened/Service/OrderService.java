package com.Ecommerce.AmazonBackened.Service;

import com.Ecommerce.AmazonBackened.Enum.ProductStatus;
import com.Ecommerce.AmazonBackened.Exception.CardNotFoundException;
import com.Ecommerce.AmazonBackened.Exception.CustomerNotFoundException;
import com.Ecommerce.AmazonBackened.Exception.InsufficientItemException;
import com.Ecommerce.AmazonBackened.Exception.ProductNotFound;
import com.Ecommerce.AmazonBackened.Model.*;
import com.Ecommerce.AmazonBackened.Repository.CardRepository;
import com.Ecommerce.AmazonBackened.Repository.CustomerRepository;

import com.Ecommerce.AmazonBackened.Repository.ProductRepository;
import com.Ecommerce.AmazonBackened.RequestDTO.OrderRequestDTO;

import com.Ecommerce.AmazonBackened.ResponseDTO.OrderResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CardRepository cardRepository;

    @Autowired
    private JavaMailSender emailSender;//to send the email


    //directly buy an item after viewing
    public OrderResponseDTO  directBuyItem(OrderRequestDTO orderRequestDTO) throws Exception {
        //part1 check if customer,product,card is valid
        Customer customer;
        try{
            customer =   customerRepository.findById(orderRequestDTO.getCustomerId()).get();
        }
        catch (Exception e){
            throw new CustomerNotFoundException("Customer not found");
        }
        Product product;
        try{
            product = productRepository.findById(orderRequestDTO.getProductId()).get();
        }
        catch (Exception e){
            throw new ProductNotFound("product not found");
        }
        Card card;
        try{
            card = cardRepository.findByCardNo(orderRequestDTO.getCardNo());
        }
        catch (Exception e){
            throw new CardNotFoundException("Invalid cardNo");
        }

        //check if required quantity is grater than available
        if(orderRequestDTO.getRequiredQuantity()>product.getQuantity())
            throw new InsufficientItemException("Insufficient Item");

        //2nd part make  order object

        Ordered order  = new Ordered();

        int item_price = orderRequestDTO.getRequiredQuantity()* product.getPrice();
        int delivery_charge = 0;

        if(item_price<500)delivery_charge=40;

        int totalCost  = item_price + delivery_charge;

        order.setDeliveryCharge(delivery_charge);
        order.setTotalCost(totalCost);
        order.setCustomer(customer);//set the customer

        String cardUse ="";
        for(int i=0;i<card.getCardNo().length();i++){
            if(i>5)cardUse+=card.getCardNo().charAt(i);
            else cardUse+="X";
        }

        order.setCardUsed(cardUse);//set the card used



        //3 part make a item object and set its attribute
        Item newItem = new Item();
        newItem.setRequiredQuantity(orderRequestDTO.getRequiredQuantity());
        newItem.setProduct(product);//set the product
        newItem.setOrder(order);

        order.getItemList().add(newItem);
        //add the order in our itemList

        //part4 update the customer entity
        customer.getOrderedList().add(order);

        //part5 update the product entity
        int remainingQuantity = product.getQuantity()-orderRequestDTO.getRequiredQuantity();
        if(remainingQuantity<=0){
            product.setQuantity(0);
            product.setStatus(ProductStatus.OUT_OF_STOCK);
        }
        product.setQuantity(remainingQuantity);



        //save the customer as it will save the order by cascading which in return save item as it is its child entity
        Customer saveCustomer = customerRepository.save(customer);
        Ordered savedOrder = saveCustomer.getOrderedList().get(saveCustomer.getOrderedList().size()-1);

        //to send the email to the student email
//        String text = "You have successfully purchased "+orderRequestDTO.getRequiredQuantity()+" "+product.getName()+" for Rs."+totalCost;
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("backenedmarchsb@gmail.com");
//        message.setTo(customer.getEmail());
//        message.setSubject("Order Success Notification");
//        message.setText(text);
//        emailSender.send(message);


        //create the orderResponse object
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setProductName(product.getName());
        orderResponseDTO.setOrderDate(savedOrder.getOrderDate());
        orderResponseDTO.setQuantityOrdered(orderRequestDTO.getRequiredQuantity());
        orderResponseDTO.setCardUsed(cardUse);
        orderResponseDTO.setItemPrice(product.getPrice());
        orderResponseDTO.setTotalCost(savedOrder.getTotalCost());
        orderResponseDTO.setDeliveryCharge(savedOrder.getDeliveryCharge());


        return orderResponseDTO;

    }


}
