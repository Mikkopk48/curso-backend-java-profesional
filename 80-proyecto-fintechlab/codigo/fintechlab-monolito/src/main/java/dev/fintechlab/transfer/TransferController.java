package dev.fintechlab.transfer;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.UUID;

@RestController @RequestMapping("/api/transfers")
public class TransferController {
    private final TransferApplicationService service; private final TransferRepository transfers; private final TransferMapper mapper;
    public TransferController(TransferApplicationService service,TransferRepository transfers,TransferMapper mapper){this.service=service;this.transfers=transfers;this.mapper=mapper;}
    @PostMapping @PreAuthorize("hasAuthority('transfers:write')")
    ResponseEntity<TransferResponse> create(@RequestHeader(value="Idempotency-Key",required=false) String key,@Valid @RequestBody CreateTransferRequest request){var result=service.execute(key,request);if(result.replayed())return ResponseEntity.ok().header("Idempotent-Replayed","true").body(result.response());return ResponseEntity.created(URI.create("/api/transfers/"+result.response().id())).body(result.response());}
    @GetMapping("/{id}") public TransferResponse find(@PathVariable UUID id){return transfers.findById(id).map(mapper::toResponse).orElseThrow(()->new dev.fintechlab.error.ResourceNotFoundException("TRANSFER_NOT_FOUND","Transferencia inexistente"));}
}
