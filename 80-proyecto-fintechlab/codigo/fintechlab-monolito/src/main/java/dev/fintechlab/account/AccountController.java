package dev.fintechlab.account;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.*;

@RestController @RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService service;
    public AccountController(AccountService service){this.service=service;}
    @PostMapping ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request){var value=service.create(request);return ResponseEntity.created(URI.create("/api/accounts/"+value.id())).body(value);}
    @GetMapping public List<AccountResponse> list(){return service.list();}
    @GetMapping("/{id}") public AccountResponse find(@PathVariable UUID id){return service.find(id);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void close(@PathVariable UUID id){service.close(id);}
}
