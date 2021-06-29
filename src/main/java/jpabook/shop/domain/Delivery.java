package jpabook.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Table(name = "deliverys")
@Entity
public class Delivery {
	@Column(name = "delivery_id")
	@GeneratedValue
	@Id
	private Long id;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;

	@Embedded
	private Address address;

	@OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
	private Order order;

	public void setOrder(Order order) {
		this.order = order;
	}
}
