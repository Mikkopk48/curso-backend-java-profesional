package dev.fintechlab.account;
import java.math.BigDecimal;
import java.util.UUID;
public record AccountResponse(UUID id,UUID customerId,String currency,BigDecimal balance,String alias,boolean active,long version){
    static AccountResponse from(AccountEntity a){return new AccountResponse(a.getId(),a.getCustomerId(),a.getCurrency(),a.getBalance(),a.getAlias(),a.isActive(),a.getVersion());}
}
