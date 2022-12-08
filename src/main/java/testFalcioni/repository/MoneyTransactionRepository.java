package testFalcioni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import testFalcioni.model.MoneyTransaction;

@Repository
public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction, String>{

}
