package com.udemy.ecommerce.dto;

import com.udemy.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order,Long> {

    // behind the sense it is
    // SELECT * FROM orders
    //LEFT OUTER JOIN customer
    //ON orders.customer_id=customer.id
    //WHERE customer.email=:email
    // ORDER BY orders.date_created DESC

    Page<Order> findByCustomerEmailOrderByDateCreatedDesc(@Param("email") String email, Pageable pageable);
    // http://localhost:8080/api/orders/search/findByCustomerEmail?email=maysaracs1001@gmail.com
}
