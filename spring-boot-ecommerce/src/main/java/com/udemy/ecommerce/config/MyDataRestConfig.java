package com.udemy.ecommerce.config;

import com.udemy.ecommerce.entity.Product;
import com.udemy.ecommerce.entity.ProductCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

// to be able to be scanned as given item
@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        // set up an array of methods to say these are the unsupported actions
        HttpMethod[] unsupportedMethods = {HttpMethod.PUT,HttpMethod.POST, HttpMethod.DELETE};

        // disable the PUT, POST and DELETE methods
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedMethods));
//        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedMethods));
    }

}
