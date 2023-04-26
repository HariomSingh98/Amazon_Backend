package com.Ecommerce.AmazonBackened.Repository;

import com.Ecommerce.AmazonBackened.Model.Ordered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedRepository extends JpaRepository<Ordered,Integer> {
}
