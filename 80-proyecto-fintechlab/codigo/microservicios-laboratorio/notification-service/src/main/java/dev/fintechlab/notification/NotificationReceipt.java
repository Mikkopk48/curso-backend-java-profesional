package dev.fintechlab.notification;
import java.time.Instant;
import java.util.UUID;
public record NotificationReceipt(UUID notificationId,UUID transferId,String status,Instant acceptedAt) {}
