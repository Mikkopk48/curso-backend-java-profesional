package dev.fintechlab.transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface TransferRepository extends JpaRepository<TransferEntity,UUID>{Optional<TransferEntity> findByIdempotencyKey(String key);}
