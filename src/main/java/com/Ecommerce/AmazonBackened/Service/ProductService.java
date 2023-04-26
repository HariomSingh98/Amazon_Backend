package com.Ecommerce.AmazonBackened.Service;

import com.Ecommerce.AmazonBackened.Enum.Category;
import com.Ecommerce.AmazonBackened.Exception.CategoryNotFound;
import com.Ecommerce.AmazonBackened.Exception.SellerNotFoundException;
import com.Ecommerce.AmazonBackened.Model.Product;
import com.Ecommerce.AmazonBackened.Model.Seller;
import com.Ecommerce.AmazonBackened.Repository.ProductRepository;
import com.Ecommerce.AmazonBackened.Repository.SellerRepository;
import com.Ecommerce.AmazonBackened.RequestDTO.ProductRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.ProductResponseDTO;
import com.Ecommerce.AmazonBackened.converter.ProductConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
  @Autowired
  SellerRepository sellerRepository;

  @Autowired
  ProductRepository productRepository;

  public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) throws SellerNotFoundException {
      Seller seller;
      try{//check if the seller is correct
          seller = sellerRepository.findById(productRequestDTO.getSeller_id()).get();
      }
      catch(Exception e){
         throw new SellerNotFoundException("Invalid Seller_id");
      }
      Product product = ProductConvertor.dtoToProduct(productRequestDTO);

      product.setSeller(seller);//set the seller manually

      seller.getProductList().add(product);


      sellerRepository.save(seller);

      ProductResponseDTO productResponseDTO = ProductConvertor.productToDto(product);

      return productResponseDTO;
  }

  public List<ProductResponseDTO> getProductByCategory(Category category) throws CategoryNotFound {
      List<Product> products ;
      try{
          products = productRepository.findAllByCategory(category);
      }
      catch (Exception e){
          throw  new CategoryNotFound("Invalid Category");
      }
      List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();
      for(Product p : products){
          ProductResponseDTO productResponseDTO = ProductConvertor.productToDto(p);
          productResponseDTOList.add(productResponseDTO);
      }
      return productResponseDTOList;
  }

}
