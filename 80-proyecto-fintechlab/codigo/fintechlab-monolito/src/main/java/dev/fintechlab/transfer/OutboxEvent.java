package dev.fintechlab.transfer;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="outbox_event")
public class OutboxEvent {
    @Id @Column(columnDefinition="binary(16)") private UUID id;
    @Column(name="aggregate_id",nullable=false,columnDefinition="binary(16)") private UUID aggregateId;
    @Column(name="event_type",nullable=false,length=80) private String eventType;
    @JdbcTypeCode(SqlTypes.JSON) @Column(nullable=false,columnDefinition="json") private String payload;
    @Column(name="created_at",nullable=false) private Instant createdAt;
    @Column(name="published_at") private Instant publishedAt;
    protected OutboxEvent() {}
    private OutboxEvent(UUID aggregateId,String eventType,String payload,Instant at){this.id=UUID.randomUUID();this.aggregateId=aggregateId;this.eventType=eventType;this.payload=payload;this.createdAt=at;}
    public static OutboxEvent transferCompleted(UUID id,Instant at){return new OutboxEvent(id,"TransferCompleted","{\"transferId\":\""+id+"\"}",at);}
}
