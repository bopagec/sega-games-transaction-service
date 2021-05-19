package com.sega.game.transaction.repository;

import com.sega.game.transaction.domain.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Collection<Transaction> findByUserId(long userId);

    Collection<Transaction> findByProductId(long productId);
}
