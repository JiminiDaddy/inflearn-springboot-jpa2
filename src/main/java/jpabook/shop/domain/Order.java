package jpabook.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Table(name = "orders")
@Entity
public class Order {
	@Column(name = "order_id")
	@GeneratedValue
	@Id
	private Long id;

	@JoinColumn(name = "member_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	@JoinColumn(name = "delivery_id")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Delivery delivery;

	private LocalDateTime orderDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public void setMember(Member member) {
		this.member = member;
		// TODO 디미터 법칙에 어긋나는데 이렇게 말고는 방법이 없을까?
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}
}
