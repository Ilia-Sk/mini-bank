package org.example.bankdemo.repository;

import org.example.bankdemo.entity.Transfer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
    @EntityGraph(attributePaths = {"fromAccount", "toAccount" })
    List<Transfer> findByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(Long fromId, Long toId);
}
