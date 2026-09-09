package dev.fintechlab.inicio.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.fintechlab.inicio.error.CustomerEmailAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository repository;

    @InjectMocks
    CustomerService service;

    @Test
    void rejectsDuplicatedEmailWithoutSaving() {
        CreateCustomerRequest request =
                new CreateCustomerRequest("Ana Demo", "ANA@example.test");
        when(repository.existsByEmailIgnoreCase("ana@example.test")).thenReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(CustomerEmailAlreadyExistsException.class);

        verify(repository, never()).save(any());
    }
}
