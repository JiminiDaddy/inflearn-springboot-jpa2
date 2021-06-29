package jpabook.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Table(name = "categorys")
@Entity
public class Category {
	@Column(name = "category_id")
	@GeneratedValue
	@Id
	private Long id;

	private String name;

	@JoinTable(name = "category_item",
		joinColumns = @JoinColumn(name = "category_id"),
		inverseJoinColumns = @JoinColumn(name = "item_id")
	)
	// 실무에서는 ManyToMany는 사용안하는게좋다. 대신 1:N과 N:1로 만들고 Mapping에 사용할 테이블을 추가하라.
	@ManyToMany
	private List<Item> items = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	private Category parent;

	@OneToMany(mappedBy = "parent")
	private List<Category> childs = new ArrayList<>();

	public void addChild(Category child) {
		childs.add(child);
		child.setParent(this);
	}
}
