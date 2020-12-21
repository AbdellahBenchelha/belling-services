package com.ab.bellingservices.web;

import com.ab.bellingservices.entities.Bill;
import com.ab.bellingservices.feign.CustomerRestClient;
import com.ab.bellingservices.feign.ProductItemRestClient;
import com.ab.bellingservices.model.Customer;
import com.ab.bellingservices.model.Product;
import com.ab.bellingservices.repositories.BillRepository;
import com.ab.bellingservices.repositories.ProductItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class  BillingRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;

    public BillingRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }


    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill=billRepository.findById(id).get();
        Customer customer=customerRestClient.getCustomerById(bill.getCustomerID());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi -> {
            Product product=productItemRestClient.getProductById(pi.getProductID());
           // pi.setProduct(product);
            pi.setProductName(product.getName());
        });

        return bill;
    }
}
