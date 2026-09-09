package dev.fintechlab.customer;
import java.util.UUID;
public record CustomerResponse(UUID id, String fullName, String email) {
    static CustomerResponse from(CustomerEntity value){return new CustomerResponse(value.getId(),value.getFullName(),value.getEmail());}
}
