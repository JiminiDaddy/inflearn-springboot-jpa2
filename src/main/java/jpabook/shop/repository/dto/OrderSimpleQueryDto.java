package jpabook.shop.repository.dto;

import jpabook.shop.domain.Address;
import jpabook.shop.domain.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/* 이 Dto는 OrderRepository에서 성능 튜닝을 목적으로 특정상황에서 사용할 목적으로 만들어졌기 때문에 repository.dto 패키지에 추가하였다.
 * SQL SELECT 절에 너무 많은 컬럼을 조회하지 않고, 필요한 컬럼만 조회하기위해 만들었다.
 * 조회할 컬럼 수가 적다면 fetch join한 결과와 성능 차이가 미미하므로 굳이 만들 필요는 없다.
 * 또한 Repository에서 이런 Dto에 의존하므로 특정 API에서만 사용 가능하다. (재사용, 확장이 어려움)
 */

@Getter
public class OrderSimpleQueryDto {
	private Long orderId;

	private String memberName;

	private LocalDateTime orderDate;

	private OrderStatus orderStatus;

	private Address address;

	public OrderSimpleQueryDto(Long orderId, String memberName, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
		this.orderId = orderId;
		this.memberName = memberName;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.address = address;
	}
}
