package com.Ecommerce.AmazonBackened.Repository;

import com.Ecommerce.AmazonBackened.Enum.Category;
import com.Ecommerce.AmazonBackened.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {


    List<Product> findAllByCategory(Category category);
}
