package com.Ecommerce.AmazonBackened.Controller;


import com.Ecommerce.AmazonBackened.Exception.CustomerNotFoundException;
import com.Ecommerce.AmazonBackened.RequestDTO.CustomerRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.CustomerResponseDTO;
import com.Ecommerce.AmazonBackened.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    CustomerService service;
    //add a customer
    @PostMapping("/add")
    public CustomerResponseDTO addCustomer(@RequestBody  CustomerRequestDTO customerRequestDTO) {
        return service.addCustomer(customerRequestDTO);
    }

    //get customer by id
    @GetMapping("/find/{id}")
    public ResponseEntity findCustomer(@PathVariable int id) {
        CustomerResponseDTO customerResponseDTO;
        try{
         customerResponseDTO = service.findCustomer(id);
        }
        catch(CustomerNotFoundException e ){
            return  new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerResponseDTO,HttpStatus.ACCEPTED);
    }
    //view all customer
    @GetMapping("all_customer")
    public List<CustomerResponseDTO> getAllCustomer(){
        return service.getAllCustomer();
    }


    //get a customer by email
    @GetMapping("/email")
    public CustomerResponseDTO customerByEmail(@RequestParam String email) throws CustomerNotFoundException {
        return service.customerByEmail(email);
    }
    //update a customer
    @PutMapping("/update")
    public ResponseEntity updateCustomerMobile(@RequestParam int id , @RequestParam String mobNo ){
        try{
            service.updateCustomerMobile(id,mobNo);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Successfully updated Mobile number",HttpStatus.ACCEPTED);
    }


    //delete customer by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCustomer(@PathVariable int id){
        try{
            service.deleteCustomer(id);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Successfully Deleted the customer",HttpStatus.ACCEPTED);
    }
}
