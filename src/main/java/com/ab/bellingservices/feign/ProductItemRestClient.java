package com.ab.bellingservices.feign;


import com.ab.bellingservices.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INVENTORY-SERVICE")
public interface ProductItemRestClient {
    @GetMapping(path = "/products")
    //PagedModel<Product> pageProduct(@RequestParam(name="page") int page,@RequestParam(name="size") int size);
    PagedModel<Product> pageProduct();
    @GetMapping(path = "/products/{id}")
    Product getProductById(@PathVariable Long id);
}
