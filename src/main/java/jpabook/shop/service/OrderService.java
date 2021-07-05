package jpabook.shop.service;

import jpabook.shop.api.dto.OrderResponseDto;
import jpabook.shop.api.dto.OrderSimpleResponseDto;
import jpabook.shop.repository.OrderRepository;
import jpabook.shop.repository.dto.OrderItemQueryDto;
import jpabook.shop.repository.dto.OrderQueryDto;
import jpabook.shop.repository.dto.OrderSimpleQueryDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service
public class OrderService {
	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	// findAllSimple -> for OrderSimpleApiController
	@Transactional(readOnly = true)
	public List<OrderSimpleResponseDto> findAllSimpleV2() {
		return orderRepository.findAll().stream()
			.map(OrderSimpleResponseDto::new)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<OrderSimpleResponseDto> findAllSimpleV3() {
		// JPQL 사용
		return orderRepository.findAllWithMemberDelivery().stream()
			.map(OrderSimpleResponseDto::new)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<OrderSimpleQueryDto> findAllSimpleV4() {
		// Repository에서 Entity가 아니라 아예 전용 Dto 타입으로 결과를 가공함
		return orderRepository.findAllToOrderSimpleQueryDtos();
	}

	// findAll -> for OrderApiController
	@Transactional(readOnly = true)
	public List<OrderResponseDto> findAllV2() {
		return orderRepository.findAll().stream()
			.map(order -> new OrderResponseDto(order))
			.collect(Collectors.toList());
	}

	public List<OrderResponseDto> findAllV3() {
		return orderRepository.findAllWithItem().stream()
			.map(order -> new OrderResponseDto(order))
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<OrderResponseDto> findAllV3ForPaging(int offset, int pageSize) {
		// JPQL 사용
		System.out.println("offset: <" + offset + ">, pageSize: <" + pageSize + ">");
		return orderRepository.findAllWithMemberDelivery(PageRequest.of(offset, pageSize)).stream()
		//return orderRepository.findAllWithMemberDelivery(PageRequest.of(1, 20)).stream()
			.map(order -> new OrderResponseDto(order))
			.collect(Collectors.toList());
	}

	@Transactional
	public List<OrderQueryDto> findAllV4() {
		// xToOne 연관관계의 데이터를 조회한다. (xToOne은 join으로 인한 row수 증가가 없다)
		List<OrderQueryDto> orderQueryDtos = orderRepository.findOrders();
		return orderQueryDtos.stream()
			.peek(order -> {
				// 각 order를 순회하면서, orderId에 해당하는 orderItems를 조회한다.
				// toMany이므로 row수가 증가할 수 있다. 따라서 한번에 컬렉션까지 조회하지않고, toOne과 toMany를 분리하여 조회한다.
				// Order 수 만큼 OrderItems를 조회하므로, Order 수(N)개 만큼 SQL이 전송된다. (N + 1 발생)
				List<OrderItemQueryDto> orderItems = orderRepository.findOrderItems(order.getOrderId());
				order.setOrderItems(orderItems);
			})
			.collect(Collectors.toList());
	}

	public List<OrderQueryDto> findAllV5() {
		// 1. 모든 Order 데이터를 조회한다.
		List<OrderQueryDto> orders = orderRepository.findOrders();
		// 2. 조회한 Orders로부터 Id만을 추출하여 List로 만든다.
		List<Long> orderIds = orders.stream().map(order -> order.getOrderId()).collect(Collectors.toList());
		// 3. 추출한 OrderIds로부터 OrderItems를 조회한다. orderIds은 컬렉션으로, where in절을 통해 한번에 SQL을 전송할 수 있다.
		Map<Long, List<OrderItemQueryDto>> orderItemMap = orderRepository.findOrderItems(orderIds).stream().collect(Collectors.groupingBy(orderItem -> orderItem.getOrderId()));
		// 4. Orders를 순회하면서 OrderId에 해당하는 OrderItems를 설정해준다.
		orders.forEach(order -> order.setOrderItems(orderItemMap.get(order.getOrderId())));
		// 따라서 Order를 조회하는 SQL 1번, OrderItems를 조회 1번, 총 2번 SQL을 전송한다. (v4의 N+1문제가 해결됨)
		return orders;
	}
}
