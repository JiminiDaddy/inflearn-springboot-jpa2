package jpabook.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Table(name = "order_item")
@Entity
public class OrderItem {
	@Column(name = "order_item_id")
	@GeneratedValue
	@Id
	private Long id;

	@JoinColumn(name = "item_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Item item;

	@JoinColumn(name = "order_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;

	private int orderPrice;

	private int count;

	public void setOrder(Order order) {
		this.order = order;
	}
}
