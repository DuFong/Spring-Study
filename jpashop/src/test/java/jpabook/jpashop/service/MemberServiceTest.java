package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception
    {
        Member member1 = new Member();
        member1.setName("T_First");

        Long savedId = memberService.join(member1);

        assertEquals(member1, memberRepository.findById(savedId));
    }

    @Test(expected = IllegalStateException.class) // -> 이것 덕분에 try/catch를 안써두됨.
    public void 중복_회원_확인() throws Exception
    {
        Member member1 = new Member();
        member1.setName("Dupli");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("Dupli");

        memberService.join(member2); // 예외가 발생해야 함

    }

}