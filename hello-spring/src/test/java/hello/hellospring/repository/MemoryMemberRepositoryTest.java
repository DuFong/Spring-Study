package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach()
    {
        repository.clearStore();
    }

    @Test
    public void save()
    {
        Member member1 = new Member();
        member1.setName("First");

        repository.save(member1);

        Member result = repository.findById(member1.getId()).get();
        assertThat(member1).isEqualTo(result);
    }

    @Test
    public void findByName()
    {
        Member member1 = new Member();
        member1.setName("First");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Second");
        repository.save(member2);

        Member result = repository.findByName(member2.getName()).get();

        assertThat(member2).isEqualTo(result);
    }

    @Test
    public void findAll()
    {
        Member member1 = new Member();
        member1.setName("First");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Second");
        repository.save(member2);

        Member member3 = new Member();
        member3.setName("Third");
        repository.save(member3);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(3);

    }

}
