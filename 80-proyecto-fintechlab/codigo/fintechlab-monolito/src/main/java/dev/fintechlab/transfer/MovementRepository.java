package dev.fintechlab.transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface MovementRepository extends JpaRepository<MovementEntity,UUID>{}
