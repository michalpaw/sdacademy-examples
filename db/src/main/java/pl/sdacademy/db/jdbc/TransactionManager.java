package pl.sdacademy.db.jdbc;

public interface TransactionManager {
	void transfer(String fromAccount,String toAccount, Double amount);

}
