package jpabook.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Getter
// 값 타입: 반드시 불변으로 만들어야한다. 따라서 Setter는 생략되었다.
@Embeddable
public class Address {
	private String city;

	private String street;

	private String zipcode;

	// Hibernate가 Reflection을 사용해서 Address를 생성할 때 기본 생성자를 이용하는 것 외에 생성을 못하게 막기위해 protected로 기본 생성자를 정의함
	protected Address() {}
}
