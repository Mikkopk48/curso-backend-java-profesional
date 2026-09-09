package dev.fintechlab.transfer;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="transfer",uniqueConstraints=@UniqueConstraint(name="uk_transfer_idempotency",columnNames="idempotency_key"))
public class TransferEntity {
    @Id @Column(columnDefinition="binary(16)") private UUID id;
    @Column(name="origin_account_id",nullable=false,columnDefinition="binary(16)") private UUID originAccountId;
    @Column(name="destination_account_id",nullable=false,columnDefinition="binary(16)") private UUID destinationAccountId;
    @Column(nullable=false,precision=19,scale=2) private BigDecimal amount;
    @Column(nullable=false,length=3) private String currency;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=24) private TransferStatus status;
    @Column(name="idempotency_key",nullable=false,length=100) private String idempotencyKey;
    @Column(name="request_fingerprint",nullable=false,length=64) private String requestFingerprint;
    @Column(name="created_at",nullable=false) private Instant createdAt;
    protected TransferEntity() {}
    private TransferEntity(UUID id,UUID origin,UUID destination,BigDecimal amount,String currency,String key,String fingerprint,Instant createdAt){
        this.id=id;this.originAccountId=origin;this.destinationAccountId=destination;this.amount=amount;this.currency=currency;
        this.status=TransferStatus.COMPLETED;this.idempotencyKey=key;this.requestFingerprint=fingerprint;this.createdAt=createdAt;
    }
    public static TransferEntity completed(UUID origin,UUID destination,BigDecimal amount,String currency,String key,String fingerprint,Instant at){return new TransferEntity(UUID.randomUUID(),origin,destination,amount,currency,key,fingerprint,at);}
    public UUID getId(){return id;} public UUID getOriginAccountId(){return originAccountId;} public UUID getDestinationAccountId(){return destinationAccountId;}
    public BigDecimal getAmount(){return amount;} public String getCurrency(){return currency;} public TransferStatus getStatus(){return status;}
    public String getIdempotencyKey(){return idempotencyKey;} public String getRequestFingerprint(){return requestFingerprint;} public Instant getCreatedAt(){return createdAt;}
}
