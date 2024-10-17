package ar.edu.unq.dapp_api.repositories;

import ar.edu.unq.dapp_api.model.OperationIntent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationIntentRepository extends JpaRepository<OperationIntent, Long> {
}
