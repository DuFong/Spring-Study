package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest  // 실제 Spring Container로 테스트 실행
@Transactional  // ** DB에 날린 쿼리들 테스트가 끝난 후 Rolled Back 함. -> delete 할 필요없이 반복 테스트 가능 !!
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

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