package dev.fintechlab.transfer;

import dev.fintechlab.account.*;
import dev.fintechlab.error.BusinessConflictException;
import org.junit.jupiter.api.*;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TransferApplicationServiceTest {
    @Mock AccountRepository accounts; @Mock TransferRepository transfers; @Mock MovementRepository movements; @Mock OutboxRepository outbox;
    AutoCloseable mocks; TransferApplicationService service;
    @BeforeEach void setup(){mocks=MockitoAnnotations.openMocks(this);service=new TransferApplicationService(accounts,transfers,movements,outbox,Mappers.getMapper(TransferMapper.class),Clock.fixed(Instant.parse("2026-01-01T00:00:00Z"),ZoneOffset.UTC));}
    @AfterEach void close()throws Exception{mocks.close();}
    @Test void movesMoneyAndCreatesEvidence(){
        var origin=AccountEntity.open(UUID.randomUUID(),"ARS",new BigDecimal("100.00"),"Origen");var destination=AccountEntity.open(UUID.randomUUID(),"ARS",BigDecimal.ZERO,"Destino");
        when(transfers.findByIdempotencyKey("key-1")).thenReturn(Optional.empty());when(accounts.lockAllById(anyCollection())).thenReturn(List.of(origin,destination));when(transfers.saveAndFlush(any())).thenAnswer(i->i.getArgument(0));
        var result=service.execute("key-1",new CreateTransferRequest(origin.getId(),destination.getId(),new BigDecimal("40.00"),"ARS"));
        assertThat(result.replayed()).isFalse();assertThat(origin.getBalance()).isEqualByComparingTo("60.00");assertThat(destination.getBalance()).isEqualByComparingTo("40.00");verify(movements).saveAll(anyList());verify(outbox).save(any());
    }
    @Test void rejectsInsufficientFunds(){
        var origin=AccountEntity.open(UUID.randomUUID(),"ARS",new BigDecimal("10.00"),"Origen");var destination=AccountEntity.open(UUID.randomUUID(),"ARS",BigDecimal.ZERO,"Destino");
        when(transfers.findByIdempotencyKey(anyString())).thenReturn(Optional.empty());when(accounts.lockAllById(anyCollection())).thenReturn(List.of(origin,destination));
        assertThatThrownBy(()->service.execute("key-2",new CreateTransferRequest(origin.getId(),destination.getId(),new BigDecimal("10.01"),"ARS"))).isInstanceOf(BusinessConflictException.class);
    }
}
