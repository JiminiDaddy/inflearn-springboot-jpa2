package jpabook.shop.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
		Order order = Order.builder()
			.member(member)
			.build();

		for (OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		order.setDelivery(delivery);
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		return order;
	}

	@Builder
	public Order(Member member) {
		this.member = member;
	}

	/*
	public void setMember(Member member) {
		this.member = member;
		// TODO 디미터 법칙에 어긋나는데 이렇게 말고는 방법이 없을까?
		member.getOrders().add(this);
	}
	*/

	public void cancel() {
		if (delivery.getStatus().equals(DeliveryStatus.COMPLETED)) {
			throw new IllegalArgumentException("이미 배송완료되어 주문 취소가 불가능합니다.");
		}
		setStatus(OrderStatus.CANCEL);
		for (OrderItem orderItem : orderItems) {
		}
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public void changeDelivery(Delivery delivery) {
		setDelivery(delivery);
	}

	private void setStatus(OrderStatus status) {
		this.status = status;
	}

	private void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	private void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}
}
