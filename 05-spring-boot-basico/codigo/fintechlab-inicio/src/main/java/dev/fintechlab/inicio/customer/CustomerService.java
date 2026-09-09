package dev.fintechlab.inicio.customer;

import dev.fintechlab.inicio.error.CustomerEmailAlreadyExistsException;
import dev.fintechlab.inicio.error.CustomerNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class CustomerService {

    private final CustomerRepository repository;

    CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    CustomerResponse create(CreateCustomerRequest request) {
        String email = normalizeEmail(request.email());
        ensureEmailAvailable(email);

        CustomerEntity customer = new CustomerEntity(
                UUID.randomUUID().toString(),
                request.fullName().trim(),
                email);

        return CustomerResponse.from(repository.save(customer));
    }

    @Transactional(readOnly = true)
    List<CustomerResponse> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "fullName"))
                .stream()
                .map(CustomerResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    CustomerResponse findById(String customerId) {
        return CustomerResponse.from(findEntity(customerId));
    }

    @Transactional
    CustomerResponse update(String customerId, UpdateCustomerRequest request) {
        CustomerEntity customer = findEntity(customerId);
        String email = normalizeEmail(request.email());

        if (repository.existsByEmailIgnoreCaseAndIdNot(email, customerId)) {
            throw new CustomerEmailAlreadyExistsException();
        }

        customer.update(request.fullName().trim(), email);
        return CustomerResponse.from(customer);
    }

    @Transactional
    void delete(String customerId) {
        repository.delete(findEntity(customerId));
    }

    private CustomerEntity findEntity(String customerId) {
        return repository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    private void ensureEmailAvailable(String email) {
        if (repository.existsByEmailIgnoreCase(email)) {
            throw new CustomerEmailAlreadyExistsException();
        }
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
