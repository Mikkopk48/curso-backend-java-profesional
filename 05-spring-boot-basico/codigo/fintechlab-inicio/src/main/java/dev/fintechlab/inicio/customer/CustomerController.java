package dev.fintechlab.inicio.customer;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
class CustomerController {

    private final CustomerService service;

    CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<CustomerResponse> create(
            @Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse created = service.create(request);
        URI location = URI.create("/api/customers/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    List<CustomerResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{customerId}")
    CustomerResponse findById(@PathVariable String customerId) {
        return service.findById(customerId);
    }

    @PutMapping("/{customerId}")
    CustomerResponse update(
            @PathVariable String customerId,
            @Valid @RequestBody UpdateCustomerRequest request) {
        return service.update(customerId, request);
    }

    @DeleteMapping("/{customerId}")
    ResponseEntity<Void> delete(@PathVariable String customerId) {
        service.delete(customerId);
        return ResponseEntity.noContent().build();
    }
}
