package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired // <- 생성자가 하나일 경우 생략가능
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long join(Member member)
    {
        // 중복 검사
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member)
    {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty())
        {
            throw new IllegalStateException("이미 존재하는 회원 이름입니다.");
        }
    }

    public List<Member> findMembers()
    {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId)
    {
        return memberRepository.findById(memberId);
    }
}
