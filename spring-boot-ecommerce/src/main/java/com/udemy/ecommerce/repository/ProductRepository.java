package com.udemy.ecommerce.repository;

import com.udemy.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;


// entity type and primary key
@CrossOrigin("http://localhost:4200")
public interface ProductRepository extends JpaRepository<Product,Long> {

    // query method to match by category id , using the parameter value
    // http://localhost:8080/api/products/search/findByCategoryId?id=2
    //Behind the scenes, Spring will execute a query similar to this
    // SELECT * FROM product where category_id=?;
    Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable);

    // findByNameContaining =  Containing" ... similar to SQL: "LIKE"
    //Behind the scenes, Spring will execute a query similar to this
    //SELECT * FROM Product p
    //WHERE
    //p.name LIKE CONCAT('%', :name ,'%')
    // http://localhost:8080/api/products/search/findByNameContainingIgnoreCase?name=Python
    // to pass data to rest api
    Page<Product> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}
