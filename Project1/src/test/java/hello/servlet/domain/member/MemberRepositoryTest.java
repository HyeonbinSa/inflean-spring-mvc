package hello.servlet.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Member member = new Member("hello", 20);
        // when
        Member savedMember = memberRepository.save(member);
        // then
        Member findMember = memberRepository.findById(savedMember.getId());

        org.assertj.core.api.Assertions.assertThat(findMember).isEqualTo(savedMember);
        System.out.println("save() Clear");
    }

    @Test
    void findAll() {
        // given
        Member member1 = new Member("hello", 20);
        Member member2 = new Member("kim", 30);
        memberRepository.save(member1);
        memberRepository.save(member2);
        // when
        List<Member> result = memberRepository.findAll();
        // then
        org.assertj.core.api.Assertions.assertThat(result.size()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(result).contains(member1, member2);
        System.out.println("findAll() Clear");
    }
}