package jpabook.shop.service;

import jpabook.shop.api.dto.OrderResponseDto;
import jpabook.shop.repository.OrderRepository;
import jpabook.shop.repository.dto.OrderSimpleQueryDto;
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

	@Transactional(readOnly = true)
	public List<OrderResponseDto> findAllV2() {
		return orderRepository.findAll().stream()
			.map(OrderResponseDto::new)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<OrderResponseDto> findAllV3() {
		// JPQL 사용
		return orderRepository.findAllWithMemberDelivery().stream()
			.map(OrderResponseDto::new)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<OrderSimpleQueryDto> findAllV4() {
		// Repository에서 Entity가 아니라 아예 전용 Dto 타입으로 결과를 가공함
		return orderRepository.findAllToOrderDtos();
	}
}
