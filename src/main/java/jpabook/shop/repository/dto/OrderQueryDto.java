package jpabook.shop.repository.dto;

import jpabook.shop.domain.Address;
import jpabook.shop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(of = "orderId")		// groupby로 묶어줄 기준으로 orderId를 사용했으며, orderId로 객체들이 구분되야하므로 Eqauls,HashCode를 재정의한다.
@AllArgsConstructor
@Setter
@Getter
public class OrderQueryDto {
	private Long orderId;
	private String memberName;
	private LocalDateTime orderDate;
	private OrderStatus orderStatus;
	private Address address;
	private List<OrderItemQueryDto> orderItems;

	public OrderQueryDto(Long orderId, String memberName, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
		this.orderId = orderId;
		this.memberName = memberName;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.address = address;
	}
}
