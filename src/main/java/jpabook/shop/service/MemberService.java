package jpabook.shop.service;

import jpabook.shop.api.dto.MemberSaveRequestDto;
import jpabook.shop.api.dto.MemberResponseDto;
import jpabook.shop.api.dto.MemberUpdateRequestDto;
import jpabook.shop.domain.Member;
import jpabook.shop.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {
	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Long join(Member member) {
		Member savedMember = memberRepository.save(member);
		return savedMember.getId();
	}

	public MemberResponseDto join(MemberSaveRequestDto requestDto) {
		Member member = memberRepository.save(requestDto.toEntity());
		return new MemberResponseDto(member);
	}

	public void update(Long id, MemberUpdateRequestDto requestDto) {
		// Dirty-checing에 의해 엔티티 값만 변경해도 알아서 update 처리가 완료된다.

		// 김영한님 팁 (Command와 Query를 분리한다)
		// 여기서 Entity/Dto를 반환할 경우, 어차피 Member는 영속상태가 끝나서 외부에서 수정해도 상관없다.
		// 하지만 update라는 기능(Command)외에 여기서 Domain을 반환해버리면 조회라는 기능을 나타내는것처럼 보인다.
		// (외부 입장에서는 id를 넘겨서 Domain을 받았으므로)
		// 따라서 update (조회 + 수정) 와 같은 경우는 void로 여기서 끝내버리는것도 괜찮다. (메서드가 단일 기능 수행)
		// TODO 아직 100% 이해안됨 (난 지금까지 Dto를 반환하는 패턴만 사용해봄) 조금 더 고민해보자
		Member member = memberRepository.getOne(id);
		member.changeName(requestDto.getName());
	}

	public MemberResponseDto find(Long id) {
		return new MemberResponseDto(memberRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Wrong memberId:<" + id + ">")));
	}

	public List<MemberResponseDto> findMembers() {
		return memberRepository.findAll().stream()
			.map(MemberResponseDto::new)
			.collect(Collectors.toList());
	}
}
