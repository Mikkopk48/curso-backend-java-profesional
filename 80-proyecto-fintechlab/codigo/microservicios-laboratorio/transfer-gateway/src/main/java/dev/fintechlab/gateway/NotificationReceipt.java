package dev.fintechlab.gateway;
import java.time.Instant;
import java.util.UUID;
public record NotificationReceipt(UUID notificationId,UUID transferId,String status,Instant acceptedAt) {}
