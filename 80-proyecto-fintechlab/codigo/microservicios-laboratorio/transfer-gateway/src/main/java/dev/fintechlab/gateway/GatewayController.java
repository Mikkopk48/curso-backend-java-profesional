package dev.fintechlab.gateway;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController @RequestMapping("/lab/notifications")
public class GatewayController { private final NotificationClient client; public GatewayController(NotificationClient client){this.client=client;} @PostMapping NotificationReceipt send(@RequestHeader(value="X-Correlation-ID",defaultValue="lab-local")String correlationId,@RequestBody NotificationCommand command){return client.send(correlationId,command);} }
