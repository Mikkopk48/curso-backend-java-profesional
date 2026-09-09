package dev.fintechlab.account;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;
public record CreateAccountRequest(@NotNull UUID customerId,@NotBlank @Pattern(regexp="[A-Z]{3}") String currency,@NotNull @DecimalMin("0.00") BigDecimal openingBalance,@NotBlank @Size(max=80) String alias) {}
