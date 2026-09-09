package dev.fintechlab.gateway;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
@FeignClient(name="notification-service",configuration=NotificationFeignConfiguration.class)
public interface NotificationClient { @PostMapping("/internal/notifications") NotificationReceipt send(@RequestHeader("X-Correlation-ID")String correlationId,@RequestBody NotificationCommand command); }
