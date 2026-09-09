package dev.fintechlab.transfer;

import dev.fintechlab.account.*;
import dev.fintechlab.error.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.*;
import java.util.*;

@Service
public class TransferApplicationService {
    private final AccountRepository accounts; private final TransferRepository transfers; private final MovementRepository movements;
    private final OutboxRepository outbox; private final TransferMapper mapper; private final Clock clock;
    public TransferApplicationService(AccountRepository accounts,TransferRepository transfers,MovementRepository movements,OutboxRepository outbox,TransferMapper mapper){this(accounts,transfers,movements,outbox,mapper,Clock.systemUTC());}
    TransferApplicationService(AccountRepository accounts,TransferRepository transfers,MovementRepository movements,OutboxRepository outbox,TransferMapper mapper,Clock clock){this.accounts=accounts;this.transfers=transfers;this.movements=movements;this.outbox=outbox;this.mapper=mapper;this.clock=clock;}

    @Transactional
    public TransferResult execute(String idempotencyKey,CreateTransferRequest request){
        String key=requireKey(idempotencyKey); BigDecimal amount=money(request.amount()); String fingerprint=fingerprint(request,amount);
        var existing=transfers.findByIdempotencyKey(key);
        if(existing.isPresent()){
            if(!existing.get().getRequestFingerprint().equals(fingerprint)) throw new BusinessConflictException("IDEMPOTENCY_CONFLICT","La clave ya fue usada con otra petición");
            return new TransferResult(mapper.toResponse(existing.get()),true);
        }
        if(request.originAccountId().equals(request.destinationAccountId())) throw new InvalidRequestException("SAME_ACCOUNT","Las cuentas deben ser diferentes");
        List<UUID> ids=new ArrayList<>(List.of(request.originAccountId(),request.destinationAccountId())); ids.sort(UUID::compareTo);
        var locked=accounts.lockAllById(ids);
        if(locked.size()!=2) throw new ResourceNotFoundException("ACCOUNT_NOT_FOUND","Una cuenta no existe");
        Map<UUID,AccountEntity> byId=new HashMap<>(); locked.forEach(a->byId.put(a.getId(),a));
        var origin=byId.get(request.originAccountId()); var destination=byId.get(request.destinationAccountId());
        if(!origin.getCurrency().equals(request.currency())||!destination.getCurrency().equals(request.currency())) throw new BusinessConflictException("CURRENCY_MISMATCH","Las monedas deben coincidir");
        origin.debit(amount); destination.credit(amount); Instant now=clock.instant();
        var transfer=transfers.saveAndFlush(TransferEntity.completed(origin.getId(),destination.getId(),amount,request.currency(),key,fingerprint,now));
        movements.saveAll(List.of(MovementEntity.of(transfer.getId(),origin.getId(),amount.negate(),request.currency(),now),MovementEntity.of(transfer.getId(),destination.getId(),amount,request.currency(),now)));
        outbox.save(OutboxEvent.transferCompleted(transfer.getId(),now));
        return new TransferResult(mapper.toResponse(transfer),false);
    }
    private static String requireKey(String value){if(value==null||value.isBlank()||value.length()>100) throw new InvalidRequestException("INVALID_IDEMPOTENCY_KEY","Idempotency-Key es obligatorio y admite hasta 100 caracteres");return value.strip();}
    private static BigDecimal money(BigDecimal value){try{return value.setScale(2,RoundingMode.UNNECESSARY);}catch(ArithmeticException ex){throw new InvalidRequestException("INVALID_SCALE","El importe admite dos decimales");}}
    private static String fingerprint(CreateTransferRequest r,BigDecimal amount){String raw=r.originAccountId()+"|"+r.destinationAccountId()+"|"+amount.toPlainString()+"|"+r.currency();try{byte[] hash=MessageDigest.getInstance("SHA-256").digest(raw.getBytes(StandardCharsets.UTF_8));return HexFormat.of().formatHex(hash);}catch(NoSuchAlgorithmException ex){throw new IllegalStateException("SHA-256 no disponible",ex);}}
}
