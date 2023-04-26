package com.Ecommerce.AmazonBackened.Exception;

public class SellerNotFoundException extends  Exception {//custom excpetion class for seller id invalid
    public SellerNotFoundException(String message){
        super(message);
    }
}
