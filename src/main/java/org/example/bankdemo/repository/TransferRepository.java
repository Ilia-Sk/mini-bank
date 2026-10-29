package org.example.bankdemo.repository;

import org.example.bankdemo.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
    List<Transfer> findByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(Long fromId, Long toId);
}
