package jpabook.shop.service;

import jpabook.shop.api.dto.OrderResponseDto;
import jpabook.shop.api.dto.OrderSimpleResponseDto;
import jpabook.shop.repository.OrderRepository;
import jpabook.shop.repository.dto.OrderSimpleQueryDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
		return orderRepository.findAllToOrderDtos();
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
}
