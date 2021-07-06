package jpabook.shop.repository;

import jpabook.shop.domain.Order;
import jpabook.shop.repository.dto.OrderFlatQueryDto;
import jpabook.shop.repository.dto.OrderItemQueryDto;
import jpabook.shop.repository.dto.OrderQueryDto;
import jpabook.shop.repository.dto.OrderSimpleQueryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
	// 페치 조인을 이용하면 lazy-loading에 의한 N+1문제가 해결된다.
	@Query("select o from Order o" +
		" join fetch o.member m" +
		" join fetch o.delivery d"
	)
	List<Order> findAllWithMemberDelivery();

	@Query("select o from Order o" +
		" join fetch o.member m" +
		" join fetch o.delivery d")
	List<Order> findAllWithMemberDelivery(Pageable pageable);

	// 성능 향상을 위해 특정 Dto에 의존한다. (SELECT절 필드가 적을 경우 성능 차이가 별로 없다)
	@Query("select new jpabook.shop.repository.dto.OrderSimpleQueryDto(o.id, o.member.name , o.orderDate, o.status, o.delivery.address)" +
		" from Order o" +
		" join o.member m" +
		" join o.delivery d"
	)
	List<OrderSimpleQueryDto> findAllToOrderSimpleQueryDtos();

	// distinct 키워드를 통해서 중복된 Order가 제거된다.
	// Order와 OrderItem이 Join됨에따라 Order가 OrderItem수에 매칭하기위해 Order가 중복적으로 추가되는 현상이 발생하는데, 이 현상을 제거시켜준다.
	@Query("select distinct o from Order o" +
		" join fetch o.member m" +
		" join fetch o.delivery d" +
		" join fetch o.orderItems oi" +
		" join fetch oi.item i"
	)
	List<Order> findAllWithItem();

	@Query("select new jpabook.shop.repository.dto.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)"  +
		" from Order o" +
		" join o.member m" +
		" join o.delivery d"
	)
	List<OrderQueryDto> findOrders();

	@Query("select new jpabook.shop.repository.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
		" from OrderItem oi" +
		" join oi.item i" +
		" where oi.order.id = :orderId"
	)
	List<OrderItemQueryDto> findOrderItems(Long orderId);

	// 위와 동일한 OrderItems를 조회하지만 in절을 사용하므로 DB로 전송되는 SQL이 1번만 발생한다.
	@Query("select new jpabook.shop.repository.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
		" from OrderItem oi" +
		" join oi.item i" +
		" where oi.order.id in :orderIds"
	)
	List<OrderItemQueryDto> findOrderItems(List<Long> orderIds);

	@Query("select new jpabook.shop.repository.dto.OrderFlatQueryDto(" +
		"o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
		" from Order o" +
		" join o.member m" +
		" join o.delivery d" +
		" join o.orderItems oi" +
		" join oi.item i"
	)
	List<OrderFlatQueryDto> findAllOrderFlat();
}
