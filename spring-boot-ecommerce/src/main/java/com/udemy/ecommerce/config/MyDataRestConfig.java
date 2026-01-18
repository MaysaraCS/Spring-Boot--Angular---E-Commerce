package com.udemy.ecommerce.config;

import com.udemy.ecommerce.entity.Country;
import com.udemy.ecommerce.entity.Product;
import com.udemy.ecommerce.entity.ProductCategory;
import com.udemy.ecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;  // ← CORRECT IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// to be able to be scanned as given item
@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Value( "${allowed.origins}")
    private String[] theAllowedOrigins;

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        // set up an array of methods to say these are the unsupported actions
        HttpMethod[] unsupportedMethods = {HttpMethod.PUT,HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        // disable Http Methods for Product class
        desableHttpMethods(Product.class,config, unsupportedMethods);

        // disable Http Methods for Product Category
        desableHttpMethods(ProductCategory.class,config, unsupportedMethods);

        // disable Http Methods for Country
        desableHttpMethods(Country.class,config, unsupportedMethods);

        // disable Http Methods for State
        desableHttpMethods(State.class,config, unsupportedMethods);

        exposeIds(config);

        // configure CORS mapping
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
    }
    // disable the PUT, POST and DELETE methods
    private void desableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] unsupportedMethods) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedMethods));
    }

    // call an internal helper method to expose the ID
    private void exposeIds(RepositoryRestConfiguration config) {

        // expose entity ids
        //
        // - gets a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // - create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // - get the entity types for the entities
        for(EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // - expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}


