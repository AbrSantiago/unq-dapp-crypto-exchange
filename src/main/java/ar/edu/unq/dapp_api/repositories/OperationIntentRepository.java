package ar.edu.unq.dapp_api.repositories;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationIntentRepository extends JpaRepository<OperationIntent, Long> {

    @Query("SELECT io FROM OperationIntent io WHERE io.status = :status")
    List<OperationIntent> findActivesOperationIntents(OperationStatus status);

}
