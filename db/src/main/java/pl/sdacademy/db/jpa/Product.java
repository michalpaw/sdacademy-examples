package pl.sdacademy.db.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@NamedQueries({
	@NamedQuery(name="Product.searchByName", query="SELECT p FROM Product p WHERE p.name=:name"),
	@NamedQuery(name="Product.searchByPrice", query="SELECT p FROM Product p WHERE p.price=:price")
})
public class Product {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private Double price;

	public Product(String name, Double price) {
		this.name = name;
		this.price = price;
	}
}
