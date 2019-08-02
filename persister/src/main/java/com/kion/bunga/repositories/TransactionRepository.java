package com.kion.bunga.repositories;

import com.kion.bunga.entities.Account;
import com.kion.bunga.entities.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {


  List<Transaction> findBySender(Account sender);
}
