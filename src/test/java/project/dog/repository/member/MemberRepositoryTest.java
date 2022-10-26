package project.dog.repository.member;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.dog.domain.member.Member;
import project.dog.repository.dto.member.MemberRegisterDTO;
import project.dog.repository.dto.member.MemberUpdateDTO;

import java.util.List;

@Transactional
@Slf4j
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save(){
        MemberRegisterDTO memberDTO = new MemberRegisterDTO();
        memberDTO.setId("testId");
        memberDTO.setPassword("testPassword");
        memberDTO.setNickname("testNickName");
        memberDTO.setDogName("testDogName");
        memberDTO.setDogAge(15);

        memberRepository.save(memberDTO);

        Member findMember = memberRepository.findById("testId").orElse(null);
        Assertions.assertThat(memberDTO.getId()).isEqualTo(findMember.getId());
        Assertions.assertThat(memberDTO.getPassword()).isEqualTo(findMember.getPassword());
        Assertions.assertThat(memberDTO.getNickname()).isEqualTo(findMember.getNickname());
        Assertions.assertThat(memberDTO.getDogName()).isEqualTo(findMember.getDogName());
        Assertions.assertThat(memberDTO.getDogAge()).isEqualTo(findMember.getDogAge());
    }

    @Test
    void update(){
        MemberRegisterDTO memberRegisterDTO = new MemberRegisterDTO();
        memberRegisterDTO.setId("registId");
        memberRegisterDTO.setPassword("registPassword");
        memberRegisterDTO.setNickname("registNickName");
        memberRegisterDTO.setDogName("registDogName");
        memberRegisterDTO.setDogAge(15);
        memberRepository.save(memberRegisterDTO);
        String id = memberRegisterDTO.getId();

        MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO();
        memberUpdateDTO.setPassword("updatePassword");
        memberUpdateDTO.setNickname("updateNickname");
        memberUpdateDTO.setDogName("updateDogName");
        memberUpdateDTO.setDogAge(10);

        memberRepository.updateMember(id, memberUpdateDTO);

        Member findMember = memberRepository.findById(id).orElse(null);
        Assertions.assertThat(id).isEqualTo(findMember.getId());
        Assertions.assertThat(memberUpdateDTO.getPassword()).isEqualTo(findMember.getPassword());
        Assertions.assertThat(memberUpdateDTO.getNickname()).isEqualTo(findMember.getNickname());
        Assertions.assertThat(memberUpdateDTO.getDogName()).isEqualTo(findMember.getDogName());
        Assertions.assertThat(memberUpdateDTO.getDogAge()).isEqualTo(findMember.getDogAge());

    }

    @Test
    void findAll(){
        MemberRegisterDTO memberA = new MemberRegisterDTO();
        memberA.setId("memberAId");
        memberA.setPassword("memberAPassword");
        memberA.setNickname("memberANickName");
        memberA.setDogName("memberADogName");
        memberA.setDogAge(15);

        MemberRegisterDTO memberB = new MemberRegisterDTO();
        memberB.setId("memberBId");
        memberB.setPassword("memberBPassword");
        memberB.setNickname("memberBNickName");
        memberB.setDogName("memberBDogName");
        memberB.setDogAge(10);

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        Member findMemberA = memberRepository.findById(memberA.getId()).orElse(null);
        Member findMemberB = memberRepository.findById(memberB.getId()).orElse(null);
        List<Member> findAll = memberRepository.findAll();

        //Member도메인에 @EqualsAndHashCode 붙이면 이상없음. contains는 equals로 판단.
        Assertions.assertThat(findAll).containsOnly(findMemberA, findMemberB);

    }

    @Test
    void delete(){
        MemberRegisterDTO memberA = new MemberRegisterDTO();
        memberA.setId("memberAId");
        memberA.setPassword("memberAPassword");
        memberA.setNickname("memberANickName");
        memberA.setDogName("memberADogName");
        memberA.setDogAge(15);
        memberRepository.save(memberA);

        memberRepository.deleteMember(memberA.getId());

        boolean result = memberRepository.findById(memberA.getId()).isEmpty();
        Assertions.assertThat(result).isTrue();

    }

    @Test
    void isMember(){
        //없는 회원
        boolean isFalse = memberRepository.isMember("false");

        Assertions.assertThat(isFalse).isFalse();

        //있는 회원
        MemberRegisterDTO memberDTO = new MemberRegisterDTO();
        memberDTO.setId("testId");
        memberDTO.setPassword("testPassword");
        memberDTO.setNickname("testNickName");
        memberDTO.setDogName("testDogName");
        memberDTO.setDogAge(15);
        memberRepository.save(memberDTO);

        boolean isTrue = memberRepository.isMember("testId");

        Assertions.assertThat(isTrue).isTrue();
    }
}