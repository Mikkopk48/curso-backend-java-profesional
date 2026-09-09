package dev.fintechlab.transfer;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
public record TransferResponse(UUID id,UUID originAccountId,UUID destinationAccountId,BigDecimal amount,String currency,TransferStatus status,Instant createdAt) {}
