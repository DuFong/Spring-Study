package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository // 컴포넌트 등록을 위해
//@RequiredArgsConstructor  여기서는 일부러 쓰지 않음.
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    /* Factory 사용하고 싶을 때
    @PersistenceUnit
    private EntityManagerFactory ef;
     */

    public Long save(Member member)
    {
        em.persist(member);
        return member.getId();
    }

    public Member findById(Long id)
    {
        return em.find(Member.class, id);
    }

    public List<Member> findAll()
    {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name)
    {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }


}
