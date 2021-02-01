package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemberService memberService = new MemberService(memberRepository);

    @AfterEach
    public void afterEach()
    {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given
        Member member1 = new Member();
        member1.setName("First");

        // when
        Long savedId = memberService.join(member1);

        // then
        Member resultMember = memberService.findOne(savedId).get();
        assertThat(member1.getName()).isEqualTo(resultMember.getName());
    }

    @Test
    void 중복_회원_예외()
    {
        // given
        Member member1 = new Member();
        member1.setName("Fong");

        Member member2 = new Member();
        member2.setName("Fong");

        // when
        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 닉네임입니다.");


//        try {
//            memberService.join(member2);
//            fail();
//        }
//        catch (IllegalStateException e)
//        {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 닉네임입니다.");
//        }
        // then


    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}