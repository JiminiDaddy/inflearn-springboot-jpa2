package jpabook.shop.api;

import jpabook.shop.api.dto.MemberSaveRequestDto;
import jpabook.shop.api.dto.MemberResponseDto;
import jpabook.shop.api.dto.MemberUpdateRequestDto;
import jpabook.shop.domain.Member;
import jpabook.shop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MemberApiController {
	private final MemberService memberService;

	public MemberApiController(MemberService memberService) {
		this.memberService = memberService;
	}

	/* List<Dto> 로 반환하지 않고 ResultCode라는 반환을 위한 객체를 반환한다.
	 * List<Dto> 로 반환할 경우, JSON이 Object가 아니라 Array로 생성되어 반환된다.
	 * Array안의 원소들은 모두 같은 타입을 유지해야하므로, 다른 타입의 데이터를 추가하기 힘들다.
	 * 따라서 전체 반환 타입은 Object로 반환하고, List<Dto>와 같은 Array 데이터는 하위 원소로 추가한다.
	 */
	@GetMapping("/api/v2/members")
	public ResultCode findMembersV2() {
		List<MemberResponseDto> responseDtos = memberService.findMembers();
		@SuppressWarnings("unchecked")
		ResultCode resultCode = new ResultCode(responseDtos.size(), responseDtos);
		return resultCode;
	}

	/* v1 API의 문제점 : 요청 값으로 Entity를 직접 사용한다.
	 * Business Logic이 담겨있는 Domain(Entity)은 언제 어떻게 얼마나 변경될지 예측할 수 없다.
	 * 고객의 요청, 기획에 따라 어떤 기능이 추가/변경될지 아무도 모른다.
	 * 즉 이런 API의 구조는 Entity가 변경되면 API 스펙이 변경된다.
	 * 따라서 요청 스펙에 맞는 Dto(Data Transfer Object)를 구현하여 받도록 한다.
	 */
	@PostMapping("/api/v1/member")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}

	@PostMapping("/api/v2/member")
	public MemberResponseDto saveMemberV2(@RequestBody @Valid MemberSaveRequestDto requestDto) {
		MemberResponseDto responseDto =  memberService.join(requestDto);
		return responseDto;
	}

	@PutMapping("/api/v2/member/{id}")
	public MemberResponseDto updateMemberV2(
		@PathVariable("id") Long id,
		@RequestBody MemberUpdateRequestDto requestDto) {
		//return memberService.update(id, requestDto);		// update method 반환 타입 변경으로 인해 임시 주석
		memberService.update(id, requestDto);
		// TODO update 메서드를 분리시키고 이렇게 find를 호출하면 SQL만 한번 더 전송되는 문제가 있지 않나?
		// 영한님 말씀으론 트래픽이 많은 게 아니라면 오히려 유지보수에 좋다고 하심. 한번 써보고 판단해보자
		return memberService.find(id);
	}

	@Data
	private static class CreateMemberResponse {
		private Long id;

		CreateMemberResponse(Long id) {
			this.id = id;
		}
	}

	@AllArgsConstructor
	@Data
	private static class ResultCode<T> {
		private int count;
		private T  data;
	}
}
