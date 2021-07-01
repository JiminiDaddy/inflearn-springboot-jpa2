package jpabook.shop.api;

import jpabook.shop.api.dto.OrderResponseDto;
import jpabook.shop.service.OrderService;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderApiController {
	private final OrderService orderService;

	public OrderApiController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/api/v2/orders")
	public ResultCode<OrderResponseDto> findOrdersV2() {
		List<OrderResponseDto> orderResponseDtos =  this.orderService.findAllV2();
		ResultCode<OrderResponseDto> result = new ResultCode<>(orderResponseDtos, orderResponseDtos.size());
		return result;
	}

	@GetMapping("/api/v3/orders")
	public ResultCode<OrderResponseDto> findOrdersV3() {
		List<OrderResponseDto> orderResponseDtos = this.orderService.findAllV3();
		ResultCode<OrderResponseDto> result = new ResultCode<>(orderResponseDtos, orderResponseDtos.size());
		return result;
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
