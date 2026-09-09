package dev.fintechlab.customer;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.*;

@RestController @RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerRepository customers;
    public CustomerController(CustomerRepository customers){this.customers=customers;}
    @PostMapping ResponseEntity<CustomerResponse> create(@Valid @RequestBody CreateCustomerRequest request){
        var saved=customers.save(CustomerEntity.create(request.fullName(),request.email()));
        return ResponseEntity.created(URI.create("/api/customers/"+saved.getId())).body(CustomerResponse.from(saved));
    }
    @GetMapping public List<CustomerResponse> list(){return customers.findAll().stream().map(CustomerResponse::from).toList();}
}
