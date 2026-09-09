package dev.fintechlab.notification;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.UUID;
@RestController @RequestMapping("/internal/notifications")
public class NotificationController {
  @PostMapping ResponseEntity<NotificationReceipt> accept(@RequestHeader("X-Correlation-ID")String correlationId,@RequestBody NotificationCommand command){var receipt=new NotificationReceipt(UUID.randomUUID(),command.transferId(),"ACCEPTED",Instant.now());return ResponseEntity.accepted().body(receipt);}
}
