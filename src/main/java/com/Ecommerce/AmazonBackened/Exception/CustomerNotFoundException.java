package com.Ecommerce.AmazonBackened.Exception;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String message){
        super(message);
    }
}