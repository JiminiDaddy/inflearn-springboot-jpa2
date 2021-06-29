package jpabook.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Setter
@Getter
@DiscriminatorValue("M")
@Entity
public class Movie extends Item{
	private String director;

	private String actor;

	public Movie(String name, int price, int stockQuantity) {
		super(name, price, stockQuantity);
	}
}
