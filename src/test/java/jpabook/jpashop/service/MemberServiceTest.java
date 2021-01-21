package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("Jo");

        // when
        Long saveId = memberService.join(member);

        // then
        // test에서 @Transantional 은 기본적으로 rollback 을 하기때문에 insert를 하지 않는다.
        // 그래서 insert를 확인하고자 할 때 flush()로 확인
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("Jo");

        Member member2 = new Member();
        member2.setName("Jo");

        // when
        memberService.join(member1);
        memberService.join(member2);        // exception!

        // then
        fail("예외가 발생해야 한다.");
    }
}