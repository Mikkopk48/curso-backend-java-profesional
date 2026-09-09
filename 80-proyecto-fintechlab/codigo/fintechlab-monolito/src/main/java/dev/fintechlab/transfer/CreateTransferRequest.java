package dev.fintechlab.transfer;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;
public record CreateTransferRequest(@NotNull UUID originAccountId,@NotNull UUID destinationAccountId,@NotNull @DecimalMin("0.01") BigDecimal amount,@NotBlank @Pattern(regexp="[A-Z]{3}") String currency) {}
