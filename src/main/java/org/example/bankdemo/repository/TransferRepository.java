package org.example.bankdemo.repository;

import org.example.bankdemo.entity.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;


public interface TransferRepository extends JpaRepository<Transfer,Long> {
    @EntityGraph(attributePaths = {"fromAccount", "toAccount" })
    Page<Transfer> findByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(Long fromId, Long toId, Pageable pageable);
    Long countByFromAccountId(Long accountId);
    Long countByToAccountId(Long accountId);
    @Query("select coalesce(sum(t.amount), 0) from Transfer t where t.fromAccount.id = :accountId")
    BigDecimal sumSentByAccountId(@Param("accountId") Long accountId);
    @Query("select coalesce(sum(t.amount), 0) from Transfer t where t.toAccount.id = :accountId")
    BigDecimal sumReceivedByAccountId(@Param("accountId") Long accountId);
}
