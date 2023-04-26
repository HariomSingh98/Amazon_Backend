package com.Ecommerce.AmazonBackened.Model;

import com.Ecommerce.AmazonBackened.Enum.Category;
import com.Ecommerce.AmazonBackened.Enum.ProductStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int price;
    @Enumerated(EnumType.STRING)
    private Category category;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne
    @JoinColumn
    Seller seller;

    //no use of item as we want to keep it as one to one unidirectional mapping

}