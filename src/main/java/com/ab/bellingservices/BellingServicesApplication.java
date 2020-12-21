package com.ab.bellingservices;

import com.ab.bellingservices.entities.Bill;
import com.ab.bellingservices.entities.ProductItem;
import com.ab.bellingservices.feign.CustomerRestClient;
import com.ab.bellingservices.feign.ProductItemRestClient;
import com.ab.bellingservices.model.Customer;
import com.ab.bellingservices.model.Product;
import com.ab.bellingservices.repositories.BillRepository;
import com.ab.bellingservices.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BellingServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BellingServicesApplication.class, args);
    }
    @Bean
    CommandLineRunner start(
            BillRepository billRepository,
            ProductItemRepository productItemRepository,
            CustomerRestClient customerRestClient,
            ProductItemRestClient productItemRestClient
    ){
        return args -> {
            Customer customer=customerRestClient.getCustomerById(1L);
            System.out.println("***********************");
            System.out.println(customer.toString());

            Bill bill1=billRepository.save(new Bill(null,new Date(),null,customer.getId(),null));
            PagedModel<Product> productPagedModel=productItemRestClient.pageProduct();
            productPagedModel.forEach(p -> {
                ProductItem productItem = new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setProductID(p.getId());
                productItem.setQuantity(1+new Random().nextInt(100));
                productItem.setBill(bill1);
                productItemRepository.save(productItem);
            });


        };
    }
}
