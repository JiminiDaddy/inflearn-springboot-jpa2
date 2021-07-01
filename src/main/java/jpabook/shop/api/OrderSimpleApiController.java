package jpabook.shop.api;

import jpabook.shop.api.dto.OrderSimpleResponseDto;
import jpabook.shop.repository.dto.OrderSimpleQueryDto;
import jpabook.shop.service.OrderService;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderSimpleApiController {
	private final OrderService orderService;

	public OrderSimpleApiController(OrderService orderService) {
		this.orderService = orderService;
	}

	// V1은 실무에서 사용할 일이 없어서 (사실 V2마찬가지) 생략함
	@GetMapping("/api/v2/simple-orders")
	public ResultCode<OrderSimpleResponseDto> findOrdersV2() {
		List<OrderSimpleResponseDto> responseDtos = orderService.findAllSimpleV2();
		return new ResultCode<>(responseDtos, responseDtos.size());
	}

	@GetMapping("/api/v3/simple-orders")
	public ResultCode<OrderSimpleResponseDto> findOrdersV3() {
		List<OrderSimpleResponseDto> responseDtos = orderService.findAllSimpleV3();
		return new ResultCode<>(responseDtos, responseDtos.size());
	}

	@GetMapping("/api/v4/simple-orders")
	public ResultCode<OrderSimpleQueryDto> findOrdersV4() {
		List<OrderSimpleQueryDto> responseDtos = orderService.findAllSimpleV4();
		return new ResultCode<>(responseDtos, responseDtos.size());
	}

	@Getter
	private static class ResultCode<T> {
		private int count;
		private List<T> data;

		ResultCode(List<T> data, int count) {
			this.data = data;
			this.count = count;
		}
	}
}
