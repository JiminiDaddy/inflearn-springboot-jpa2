package jpabook.shop.api.dto;

import jpabook.shop.domain.Address;
import jpabook.shop.domain.Order;
import lombok.Getter;

@Getter
public class OrderResponseDto {
	private Long id;

	private String memberName;

	private Address address;

	public OrderResponseDto(Order order) {
		this.id = order.getId();
		this.memberName = order.getMember().getName();
		this.address = order.getDelivery().getAddress();
	}
}
