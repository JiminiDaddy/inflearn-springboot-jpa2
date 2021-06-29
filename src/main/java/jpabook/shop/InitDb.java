package jpabook.shop;

import jpabook.shop.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {
	private final InitService initService;

	@PostConstruct
	public void init() {
		initService.dbInit1();
		initService.dbInit2();
	}

	@RequiredArgsConstructor
	@Transactional
	@Component
	static class InitService {
		private final EntityManager entityManager;

		public void dbInit1() {
			Member member =createMember("userA", "서울", "송파", "11111");
			entityManager.persist(member);

			Book book1 = createBook("JPA1 Book", 50000, 10, "kim", "12345");
			entityManager.persist(book1);

			Book book2 = createBook("JPA2 Book", 80000, 20, "kim", "23234");
			entityManager.persist(book2);

			OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
			OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 2);
			Delivery delivery = createDelivery(member);

			Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
			entityManager.persist(order);
		}

		public void dbInit2() {
			Member member =createMember("userB", "수원", "광교", "22222");
			entityManager.persist(member);

			Book book1 = createBook("Spring1 Book", 25000, 3, "park", "55555");
			entityManager.persist(book1);

			Book book2 = createBook("Spring2 Book", 45000, 100, "park", "66666");
			entityManager.persist(book2);

			OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
			OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 2);
			Delivery delivery = createDelivery(member);

			Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
			entityManager.persist(order);
		}

		private Member createMember(String name, String city, String street, String zipcode) {
			return Member.builder()
				.name(name)
				.address(new Address(city, street, zipcode))
				.build();
		}

		private Book createBook(String name, int price, int stockQuantity, String author, String isbn) {
			return Book.builder()
				.name(name)
				.price(price)
				.stockQuantity(stockQuantity)
				//.author(author)
				//.isbn(isbn)
				.build();

		}

		private Delivery createDelivery(Member member) {
			Delivery delivery = new Delivery();
			delivery.setAddress(member.getAddress());
			return delivery;
		}
	}
}
