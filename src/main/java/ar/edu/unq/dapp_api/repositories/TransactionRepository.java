package ar.edu.unq.dapp_api.repositories;

import ar.edu.unq.dapp_api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT COUNT(t) FROM Transaction t " +
            "WHERE t.status = ar.edu.unq.dapp_api.model.enums.TransactionStatus.CONFIRMED " +
            "AND t.startTime BETWEEN :startDate AND :endDate")
    int findConfirmedTransactionsByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Transaction t " +
            "WHERE t.operationIntent.id = :operationIntentId " +
            "AND t.status <> ar.edu.unq.dapp_api.model.enums.TransactionStatus.CANCELLED ")
    boolean existsByOperationIntentIdNotCancelled(
            @Param("operationIntentId") Long operationIntentId
    );
}