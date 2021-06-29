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

	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

	protected Member() {}

	@Builder
	public Member(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public void changeName(String name) {
		this.name = name;
	}
}
