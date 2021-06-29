package jpabook.shop.api.dto;

import jpabook.shop.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
	private Long id;

	private String name;

	public MemberResponseDto(Member member) {
		this.id = member.getId();
		this.name = member.getName();
	}

}
