package com.Ecommerce.AmazonBackened.Service;

import com.Ecommerce.AmazonBackened.Exception.CustomerNotFoundException;
import com.Ecommerce.AmazonBackened.Model.Cart;
import com.Ecommerce.AmazonBackened.Model.Customer;
import com.Ecommerce.AmazonBackened.Repository.CustomerRepository;

import com.Ecommerce.AmazonBackened.RequestDTO.CustomerRequestDTO;

import com.Ecommerce.AmazonBackened.ResponseDTO.CustomerResponseDTO;
import com.Ecommerce.AmazonBackened.converter.CustomerConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO){

        Customer newCustomer = CustomerConvertor.DtoToCustomer(customerRequestDTO);

        //allocate a cart to customer
        Cart cart = new Cart();
        cart.setCartTotal(0);
        cart.setCustomer(newCustomer);

        newCustomer.setCart(cart);//set the cart in the customer

        customerRepository.save(newCustomer);//save the customer as well as cart

        CustomerResponseDTO customerResponseDTO = CustomerConvertor.customerToDto(newCustomer);

        return customerResponseDTO;
    }
    public CustomerResponseDTO findCustomer(int id) throws CustomerNotFoundException {
        Customer customer;
        try{
            customer =   customerRepository.findById(id).get();
        }
        catch (Exception e){
            throw new CustomerNotFoundException("Customer not found");
        }
        CustomerResponseDTO customerResponseDTO = CustomerConvertor.customerToDto(customer);

        return customerResponseDTO;
    }
    public List<CustomerResponseDTO> getAllCustomer(){

        List<Customer> customerList  =  customerRepository.findAll();

        List<CustomerResponseDTO> customerResponseDTOS =new ArrayList<>();
        for(Customer c : customerList){
            CustomerResponseDTO customerResponseDTO = CustomerConvertor.customerToDto(c);
            customerResponseDTOS.add(customerResponseDTO);
        }
        return  customerResponseDTOS;
    }
    public CustomerResponseDTO customerByEmail(String email) throws CustomerNotFoundException {
        Customer customer;
        try{
            customer =    customerRepository.findByEmail(email);
        }
        catch (Exception e){
            throw new CustomerNotFoundException("Customer not found");
        }

        CustomerResponseDTO customerResponseDTO = CustomerConvertor.customerToDto(customer);

        return customerResponseDTO;
    }

    public  void updateCustomerMobile(int id,String mobNo) throws CustomerNotFoundException {
        Customer customer;
        try{
            customer = customerRepository.findById(id).get();
        }
        catch (Exception e){
            throw new CustomerNotFoundException("Customer  not  found");
        }
        customer.setMobNo(mobNo);

        customerRepository.save(customer);
    }

    public void deleteCustomer(int id) throws CustomerNotFoundException {
        Customer customer;
        try{
            customer = customerRepository.findById(id).get();
        }
        catch (Exception e){
            throw new CustomerNotFoundException("Customer not found");
        }

        customerRepository.deleteById(id);
    }


}
