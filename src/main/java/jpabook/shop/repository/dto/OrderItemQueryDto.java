package jpabook.shop.repository.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderItemQueryDto {
	@JsonIgnore
	private Long orderId;
	private String itemName;
	private int orderPrice;
	private int count;
}
