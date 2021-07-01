package jpabook.shop.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	@Builder
	public OrderItem(Item item, int orderPrice, int count) {
		this.item = item;
		this.orderPrice = orderPrice;
		this.count = count;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
		return OrderItem.builder()
			.item(item)
			.orderPrice(orderPrice)
			.count(count).build();
	}
}
