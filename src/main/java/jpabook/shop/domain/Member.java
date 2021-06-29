package jpabook.shop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
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
}
