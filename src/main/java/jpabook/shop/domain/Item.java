package jpabook.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Entity
public abstract class Item {
	@Column(name = "item_id")
	@GeneratedValue
	@Id
	private Long id;

	private String name;

	private int price;

	private int stockQuantity;

	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();
}
