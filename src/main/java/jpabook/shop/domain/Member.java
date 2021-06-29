package jpabook.shop.domain;


import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "members")
@Entity
public class Member {

	@GeneratedValue
	@Column(name = "member_id")
	@Id
	private Long id;

	private String name;

	@Embedded
	private Address address;

	// 실무에서는 회원은 주문을 참조하지 않고, 주문에서 회원을 참조하는 단방향만 구현한다
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

	protected Member() {}

	@Builder
	public Member(Long id, String name, Address address) {
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public void changeName(String name) {
		this.name = name;
	}

	public void changeAddress(Address address) {
		this.address = address;
	}
}
