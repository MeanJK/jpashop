package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {


    private EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setName("memberA");
        //when
        Long saveMember = memberRepository.save(member);
        Member findMember = memberRepository.find(saveMember);
        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
    }
}