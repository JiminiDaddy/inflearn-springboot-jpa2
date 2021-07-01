package jpabook.shop.api.dto;

import jpabook.shop.domain.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemResponseDto {
	private String itemName;	// 상품명

	private int orderPrice;		// 주문 가격

	private int count;			// 주문 수량

	public OrderItemResponseDto(OrderItem orderItem) {
		this.itemName = orderItem.getItem().getName();
		this.orderPrice = orderItem.getOrderPrice();
		this.count = orderItem.getCount();
	}
}
