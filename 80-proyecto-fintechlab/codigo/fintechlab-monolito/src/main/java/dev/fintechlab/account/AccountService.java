package dev.fintechlab.account;

import dev.fintechlab.customer.CustomerRepository;
import dev.fintechlab.error.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class AccountService {
    private final AccountRepository accounts; private final CustomerRepository customers;
    public AccountService(AccountRepository accounts,CustomerRepository customers){this.accounts=accounts;this.customers=customers;}
    @Transactional public AccountResponse create(CreateAccountRequest r){
        if(!customers.existsById(r.customerId())) throw new ResourceNotFoundException("CUSTOMER_NOT_FOUND","Cliente inexistente");
        return AccountResponse.from(accounts.save(AccountEntity.open(r.customerId(),r.currency(),r.openingBalance(),r.alias())));
    }
    @Transactional(readOnly=true) public AccountResponse find(UUID id){return AccountResponse.from(accounts.findById(id).orElseThrow(()->new ResourceNotFoundException("ACCOUNT_NOT_FOUND","Cuenta inexistente")));}
    @Transactional(readOnly=true) public List<AccountResponse> list(){return accounts.findAll().stream().map(AccountResponse::from).toList();}
    @Transactional public void close(UUID id){accounts.findById(id).orElseThrow(()->new ResourceNotFoundException("ACCOUNT_NOT_FOUND","Cuenta inexistente")).close();}
}
