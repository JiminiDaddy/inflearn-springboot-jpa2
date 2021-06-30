package jpabook.shop.repository;

import jpabook.shop.domain.Order;
import jpabook.shop.repository.dto.OrderSimpleQueryDto;
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

	// 성능 향상을 위해 특정 Dto에 의존한다. (SELECT절 필드가 적을 경우 성능 차이가 별로 없다)
	@Query("select new jpabook.shop.repository.dto.OrderSimpleQueryDto(o.id, m.name , o.orderDate, o.status, d.address)" +
		" from Order o" +
		" join o.member m" +
		" join o.delivery d"
	)
	List<OrderSimpleQueryDto> findAllToOrderDtos();
}
