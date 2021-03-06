package com.w4springrain.datajpa.repository;

import com.w4springrain.datajpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.SourceVersion;
import java.sql.SQLOutput;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.find(savedMember.getId());
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member); //JPA 엔티티 동일성 보장
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        //단건 조회 검증
        Member findMember1 =
                memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 =
                memberJpaRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
        //리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
        //카운트 검증
        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);
        //삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        long deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);
        List<Member> result =
                memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void paging() {
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));
        memberJpaRepository.save(new Member("member6", 10));
        memberJpaRepository.save(new Member("member7", 10));
        memberJpaRepository.save(new Member("member8", 10));
        memberJpaRepository.save(new Member("member9", 10));
        memberJpaRepository.save(new Member("member10", 10));
        memberJpaRepository.save(new Member("member11", 10));
        memberJpaRepository.save(new Member("member12", 10));
        memberJpaRepository.save(new Member("member13", 10));
        memberJpaRepository.save(new Member("member14", 10));
        memberJpaRepository.save(new Member("member15", 10));
        memberJpaRepository.save(new Member("member16", 10));
        memberJpaRepository.save(new Member("member17", 10));
        memberJpaRepository.save(new Member("member18", 10));
        memberJpaRepository.save(new Member("member19", 10));
        memberJpaRepository.save(new Member("member20", 10));

        int age = 10;
        int offset = 0;
        int limit = 3;

        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(20);
    }

    @Test
    public void bulkAgePlus() {
        //given
        memberJpaRepository.save(new Member("member1", 1));
        memberJpaRepository.save(new Member("member2", 2));
        memberJpaRepository.save(new Member("member3", 3));
        memberJpaRepository.save(new Member("member4", 4));
        memberJpaRepository.save(new Member("member5", 5));
        memberJpaRepository.save(new Member("member6", 6));
        memberJpaRepository.save(new Member("member7", 7));
        memberJpaRepository.save(new Member("member8", 8));
        memberJpaRepository.save(new Member("member9", 9));
        memberJpaRepository.save(new Member("member10", 10));
        memberJpaRepository.save(new Member("member11", 11));
        memberJpaRepository.save(new Member("member12", 12));
        memberJpaRepository.save(new Member("member13", 13));
        memberJpaRepository.save(new Member("member14", 14));
        memberJpaRepository.save(new Member("member15", 15));
        memberJpaRepository.save(new Member("member16", 16));
        memberJpaRepository.save(new Member("member17", 17));
        memberJpaRepository.save(new Member("member18", 18));
        memberJpaRepository.save(new Member("member19", 19));
        memberJpaRepository.save(new Member("member20", 20));

        //when
        int resultCount = memberJpaRepository.bulkAgePlus(10);

        //then
        assertThat(resultCount).isEqualTo(11);
    }
}