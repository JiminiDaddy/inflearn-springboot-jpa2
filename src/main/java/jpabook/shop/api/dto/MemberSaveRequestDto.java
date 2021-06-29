package jpabook.shop.api.dto;

import jpabook.shop.domain.Member;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class MemberSaveRequestDto {
	@NotEmpty
	private String name;

	public Member toEntity() {
		return Member.builder()
			.name(name)
			.build();
	}
}
