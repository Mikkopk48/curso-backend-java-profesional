package dev.fintechlab.inicio.customer;

public record CustomerResponse(
        String id,
        String fullName,
        String email) {

    static CustomerResponse from(CustomerEntity entity) {
        return new CustomerResponse(entity.id(), entity.fullName(), entity.email());
    }
}
