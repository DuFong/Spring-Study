package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository)
    {
        this.memberRepository = memberRepository;
    }


    /**
     * 회원 가입
     */
    public Long join(Member member)
    {
        checkDuplicateNickname(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void checkDuplicateNickname(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 닉네임입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers()
    {
        return memberRepository.findAll();
    }

    /**
     * 회원 한명 찾기
     * @param id
     * @return Optional<Member>
     */
    public Optional<Member> findOne(Long id)
    {
        return memberRepository.findById(id);
    }

}
