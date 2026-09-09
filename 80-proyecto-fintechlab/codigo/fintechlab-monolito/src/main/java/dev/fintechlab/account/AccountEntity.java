package dev.fintechlab.account;

import dev.fintechlab.error.BusinessConflictException;
import jakarta.persistence.*;
import java.math.*;
import java.util.*;

@Entity @Table(name="account")
public class AccountEntity {
    @Id @Column(columnDefinition="binary(16)") private UUID id;
    @Column(name="customer_id",nullable=false,columnDefinition="binary(16)") private UUID customerId;
    @Column(nullable=false,length=3) private String currency;
    @Column(nullable=false,precision=19,scale=2) private BigDecimal balance;
    @Column(nullable=false,length=80) private String alias;
    @Column(nullable=false) private boolean active;
    @Version private long version;
    protected AccountEntity() {}
    private AccountEntity(UUID id, UUID customerId, String currency, BigDecimal balance, String alias){
        this.id=id; this.customerId=customerId; this.currency=Currency.getInstance(currency).getCurrencyCode();
        this.balance=money(balance); this.alias=alias.strip(); this.active=true;
        if(this.balance.signum()<0) throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
    }
    public static AccountEntity open(UUID customerId,String currency,BigDecimal balance,String alias){return new AccountEntity(UUID.randomUUID(),customerId,currency,balance,alias);}
    public void debit(BigDecimal amount){requireActive(); var next=balance.subtract(money(amount)); if(next.signum()<0) throw new BusinessConflictException("INSUFFICIENT_FUNDS","Saldo insuficiente"); balance=next;}
    public void credit(BigDecimal amount){requireActive(); balance=balance.add(money(amount));}
    public void close(){if(balance.signum()!=0) throw new BusinessConflictException("NON_ZERO_BALANCE","La cuenta debe tener saldo cero"); active=false;}
    private void requireActive(){if(!active) throw new BusinessConflictException("ACCOUNT_INACTIVE","La cuenta está inactiva");}
    private static BigDecimal money(BigDecimal value){Objects.requireNonNull(value); return value.setScale(2,RoundingMode.UNNECESSARY);}
    public UUID getId(){return id;} public UUID getCustomerId(){return customerId;} public String getCurrency(){return currency;}
    public BigDecimal getBalance(){return balance;} public String getAlias(){return alias;} public boolean isActive(){return active;} public long getVersion(){return version;}
}
