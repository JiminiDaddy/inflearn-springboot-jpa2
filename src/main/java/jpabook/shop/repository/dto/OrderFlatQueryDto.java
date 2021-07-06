package jpabook.shop.repository.dto;

import jpabook.shop.domain.Address;
import jpabook.shop.domain.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderFlatQueryDto {
	private Long orderId;
	private String memberName;
	private LocalDateTime orderDate;
	private OrderStatus orderStatus;
	private Address address;

	private String itemName;
	private int orderPrice;
	private int count;

	public OrderFlatQueryDto(Long orderId, String memberName, LocalDateTime orderDate, OrderStatus orderStatus,
							 Address address, String itemName, int orderPrice, int count) {
		this.orderId = orderId;
		this.memberName = memberName;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.address = address;
		this.itemName = itemName;
		this.orderPrice = orderPrice;
		this.count = count;
	}
}
