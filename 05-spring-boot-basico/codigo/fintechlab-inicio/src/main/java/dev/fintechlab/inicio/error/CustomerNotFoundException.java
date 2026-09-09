package dev.fintechlab.inicio.error;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String customerId) {
        super("No existe el cliente con id " + customerId);
    }
}
