package com.Ecommerce.AmazonBackened.Repository;

import com.Ecommerce.AmazonBackened.Model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository  extends JpaRepository<Seller,Integer> {

    Seller findByPanNo(String panNo);//custom find by panNo

    List<Seller> findByAge(int age);//custom find by age
}
