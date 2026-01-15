package com.udemy.ecommerce.repository;

import com.udemy.ecommerce.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "countries", path = "countries")
public interface CountryRepository extends JpaRepository<Country,Short> {
    // Country is the entity class, and Integer is the primary key

    // to retrive all countries
    // http://localhost:8080/api/countries

    // to retrive  countries with id
    // http://localhost:8080/api/countries/4


}
