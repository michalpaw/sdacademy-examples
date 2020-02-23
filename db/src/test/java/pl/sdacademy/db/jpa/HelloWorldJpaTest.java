package pl.sdacademy.db.jpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.Transaction;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HelloWorldJpaTest {

	private EntityManager entityManager;

	@BeforeEach
	void initEntityManager() {
		entityManager = Persistence.createEntityManagerFactory("hello").createEntityManager();
	}

	@Test
	@DisplayName("show how to use JPA in minimal way")
	void hello() {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Product product = new Product();
		entityManager.persist(product);
		transaction.commit();
		Product foundProduct = entityManager.find(Product.class, product.getId());
		assertThat(foundProduct).isNotNull();
	}

	@Test
	@DisplayName("show how to find product by name")
	void nameAddedTest() {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Product ps5Console = new Product("PS5 Console", 500.00);
		entityManager.persist(ps5Console);
		Product ps4Console = new Product("PS4 Console", 650.00);
		entityManager.persist(ps4Console);
		transaction.commit();
		TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.name=:name",
			Product.class);
		query.setParameter("name", "PS4 Console");
		List<Product> resultList = query.getResultList();

		assertThat(resultList).containsOnly(ps4Console);
	}

	@Test
	@DisplayName("show how to find product by price")
	void findByPriceTest() {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Product ps5Console = new Product("PS5 Console", 800.00);
		entityManager.persist(ps5Console);
		Product ps4Console = new Product("PS4 Console", 650.00);
		entityManager.persist(ps4Console);
		transaction.commit();

		TypedQuery<Product> query = entityManager.createNamedQuery("Product.searchByPrice", Product.class);
		query.setParameter("price", 650.00);
		List<Product> resultList = query.getResultList();
		assertThat(resultList).containsOnly(ps4Console);
	}

	@Test
	@DisplayName("show how to update product")
	void updateProductTest() {
		EntityTransaction persistProductTransaction = entityManager.getTransaction();
		persistProductTransaction.begin();
		Product ps6Console = new Product("PS6 Console", 1000.00);
		entityManager.persist(ps6Console);
		persistProductTransaction.commit();

		EntityTransaction updateNameTransaction = entityManager.getTransaction();
		updateNameTransaction.begin();
		Product product = entityManager.find(Product.class, ps6Console.getId());
		product.setName("Xbox");
		updateNameTransaction.commit();
	}
}
