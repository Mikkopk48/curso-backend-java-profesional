package dev.fintechlab.inicio.error;

public class CustomerEmailAlreadyExistsException extends RuntimeException {

    public CustomerEmailAlreadyExistsException() {
        super("Ya existe un cliente con ese correo");
    }
}
