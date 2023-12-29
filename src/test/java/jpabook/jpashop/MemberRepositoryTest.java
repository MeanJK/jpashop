package jpabook.jpashop;

import jakarta.persistence.EntityManager;
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
        member.setUsername("memberA");

        //when
        Long saveMember = memberRepository.save(member);
        Member findMember = memberRepository.find(saveMember);
        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    }
}