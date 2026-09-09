package dev.fintechlab.notification;
import java.util.UUID;
public record NotificationCommand(UUID transferId,String template) {}
