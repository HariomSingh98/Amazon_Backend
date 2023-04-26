package com.Ecommerce.AmazonBackened.Service;

import com.Ecommerce.AmazonBackened.Exception.ProductNotFound;
import com.Ecommerce.AmazonBackened.Model.Item;
import com.Ecommerce.AmazonBackened.Model.Product;
import com.Ecommerce.AmazonBackened.Repository.ItemRepository;
import com.Ecommerce.AmazonBackened.Repository.ProductRepository;
import com.Ecommerce.AmazonBackened.ResponseDTO.ItemResponseDTO;
import com.Ecommerce.AmazonBackened.converter.ItemConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ItemRepository itemRepository;

    public ItemResponseDTO viewItem(int id) throws ProductNotFound {//view a product with id
        Product product;//get the product from id
        try {
            product = productRepository.findById(id).get();
        } catch (Exception e) {
            throw new ProductNotFound("Invalid Product_id");
        }
        //we now have to create an item object since we are viewing the product
        Item newItem = new Item();
        newItem = ItemConvertor.newItemFromProduct(product);





        //save the item object using item  repo
        itemRepository.save(newItem);

        //create the response
        ItemResponseDTO itemResponseDTO;
        itemResponseDTO = ItemConvertor.productToDto(product);

        return itemResponseDTO;
    }

}