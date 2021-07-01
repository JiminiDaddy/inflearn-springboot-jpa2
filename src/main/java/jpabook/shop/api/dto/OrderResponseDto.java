package jpabook.shop.api.dto;

import jpabook.shop.domain.Address;
import jpabook.shop.domain.Order;
import jpabook.shop.domain.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponseDto {
	private Long orderId;

	private String memberName;

	private OrderStatus orderStatus;

	private LocalDateTime orderDate;

	private Address address;

	// Dto인데 List<OrderItem>과 같이 Entity를 전송해버리면 API안에 BusinessLogic이 포함된다.
	// Entity는 항상 변경될 수 있는 BusinessLogic이므로 OrderItem도 Dto로 변경하여 전송해야 한다.
	//private List<OrderItem> orderItems;

	private List<OrderItemResponseDto> orderItems;

	public OrderResponseDto(Order order) {
		this.orderId = order.getId();
		this.memberName = order.getMember().getName();
		this.orderStatus = order.getStatus();
		this.orderDate = order.getOrderDate();
		this.address = order.getDelivery().getAddress();

		this.orderItems = order.getOrderItems().stream()
			.map(OrderItemResponseDto::new)
			.collect(Collectors.toList());
	}
}
