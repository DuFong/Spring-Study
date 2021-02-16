package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional // *** 중요 중요 *** EntityManager를 통한 데이터 추가 및 변경에선 꼭* Transactional 이 필요하다.
    //@Rollback(false) -> 직접 디비에서 데이터를 확인할 수 있음.
    public void testMember() throws Exception
    {
        //given
        Member member1 = new Member();
        member1.setName("First");

        //when
        Long savedId = memberRepository.save(member1);
        Member searchedMember = memberRepository.find(savedId);

        //then
        Assertions.assertThat(searchedMember.getId()).isEqualTo(member1.getId());
        Assertions.assertThat(searchedMember.getName()).isEqualTo(member1.getName());
    }
}
