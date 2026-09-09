package dev.fintechlab.gateway;
import java.util.UUID;
public record NotificationCommand(UUID transferId,String template) {}
