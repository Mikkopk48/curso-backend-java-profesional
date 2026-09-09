package dev.fintechlab.transfer;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="movement")
public class MovementEntity {
    @Id @Column(columnDefinition="binary(16)") private UUID id;
    @Column(name="transfer_id",nullable=false,columnDefinition="binary(16)") private UUID transferId;
    @Column(name="account_id",nullable=false,columnDefinition="binary(16)") private UUID accountId;
    @Column(nullable=false,precision=19,scale=2) private BigDecimal amount;
    @Column(nullable=false,length=3) private String currency;
    @Column(name="occurred_at",nullable=false) private Instant occurredAt;
    protected MovementEntity() {}
    private MovementEntity(UUID transferId,UUID accountId,BigDecimal amount,String currency,Instant at){this.id=UUID.randomUUID();this.transferId=transferId;this.accountId=accountId;this.amount=amount;this.currency=currency;this.occurredAt=at;}
    public static MovementEntity of(UUID transferId,UUID accountId,BigDecimal amount,String currency,Instant at){return new MovementEntity(transferId,accountId,amount,currency,at);}
}
