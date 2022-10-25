package project.dog.repository.member;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.dog.domain.SnsMember;
import project.dog.repository.dto.member.SnsMemberRegisterDTO;
import project.dog.repository.dto.member.SnsMemberUpdateDTO;
import project.dog.repository.dto.member.SnsRefreshTokenUpdateDTO;

import java.util.List;

@SpringBootTest
@Slf4j
@Transactional
class SnsMemberRepositoryTest {

    @Autowired
    private SnsMemberRepository snsMemberRepository;

    @Test
    void save(){
        SnsMemberRegisterDTO memberDTO = new SnsMemberRegisterDTO();
        memberDTO.setSnsId("testId");
        memberDTO.setNickname("testNickname");
        memberDTO.setDogName("testDogName");
        memberDTO.setDogAge(3);
        memberDTO.setRefreshToken("testRefreshToken");

        snsMemberRepository.save(memberDTO);

        SnsMember findMember = snsMemberRepository.findById(memberDTO.getSnsId()).orElse(null);
        Assertions.assertThat(memberDTO.getSnsId()).isEqualTo(findMember.getSnsId());
        Assertions.assertThat(memberDTO.getNickname()).isEqualTo(findMember.getNickname());
        Assertions.assertThat(memberDTO.getDogName()).isEqualTo(findMember.getDogName());
        Assertions.assertThat(memberDTO.getDogAge()).isEqualTo(findMember.getDogAge());
        Assertions.assertThat(memberDTO.getRefreshToken()).isEqualTo(findMember.getRefreshToken());
    }

    @Test
    void update(){
        SnsMemberRegisterDTO memberDTO = new SnsMemberRegisterDTO();
        memberDTO.setSnsId("registerId");
        memberDTO.setNickname("registerNickname");
        memberDTO.setDogName("registerDogName");
        memberDTO.setDogAge(3);
        memberDTO.setRefreshToken("registerRefreshToken");
        snsMemberRepository.save(memberDTO);

        SnsMemberUpdateDTO updateDTO = new SnsMemberUpdateDTO();
        updateDTO.setNickname("updateNickname");
        updateDTO.setDogName("updateDogName");
        updateDTO.setDogAge(10);

        snsMemberRepository.updateMember(memberDTO.getSnsId(), updateDTO);

        SnsMember findMember = snsMemberRepository.findById(memberDTO.getSnsId()).orElse(null);
        Assertions.assertThat(updateDTO.getNickname()).isEqualTo(findMember.getNickname());
        Assertions.assertThat(updateDTO.getDogName()).isEqualTo(findMember.getDogName());
        Assertions.assertThat(updateDTO.getDogAge()).isEqualTo(findMember.getDogAge());

    }

    @Test
    void findAll(){
        SnsMemberRegisterDTO memberA = new SnsMemberRegisterDTO();
        memberA.setSnsId("A registerId");
        memberA.setNickname("A registerNickname");
        memberA.setDogName("A registerDogName");
        memberA.setDogAge(30);
        memberA.setRefreshToken("A registerRefreshToken");

        SnsMemberRegisterDTO memberB = new SnsMemberRegisterDTO();
        memberB.setSnsId("B registerId");
        memberB.setNickname("B registerNickname");
        memberB.setDogName("B registerDogName");
        memberB.setDogAge(10);
        memberB.setRefreshToken("B registerRefreshToken");

        snsMemberRepository.save(memberA);
        snsMemberRepository.save(memberB);

        List<SnsMember> result = snsMemberRepository.findAll();

        SnsMember findMemberA = snsMemberRepository.findById(memberA.getSnsId()).orElse(null);
        SnsMember findMemberB = snsMemberRepository.findById(memberB.getSnsId()).orElse(null);
        Assertions.assertThat(result).containsOnly(findMemberA, findMemberB);
    }

    @Test
    void updateRefreshToken(){
        SnsMemberRegisterDTO memberDTO = new SnsMemberRegisterDTO();
        memberDTO.setSnsId("registerId");
        memberDTO.setNickname("registerNickname");
        memberDTO.setDogName("registerDogName");
        memberDTO.setDogAge(3);
        memberDTO.setRefreshToken("registerRefreshToken");
        snsMemberRepository.save(memberDTO);

        SnsRefreshTokenUpdateDTO updateDTO = new SnsRefreshTokenUpdateDTO();
        updateDTO.setSnsId(memberDTO.getSnsId());
        updateDTO.setRefreshToken("updateRefreshToken");

        snsMemberRepository.updateRefreshToken(updateDTO);

        SnsMember findMember = snsMemberRepository.findById(updateDTO.getSnsId()).orElse(null);
        Assertions.assertThat(findMember.getRefreshToken()).isEqualTo(updateDTO.getRefreshToken());
    }

    @Test
    void delete(){
        SnsMemberRegisterDTO memberDTO = new SnsMemberRegisterDTO();
        memberDTO.setSnsId("registerId");
        memberDTO.setNickname("registerNickname");
        memberDTO.setDogName("registerDogName");
        memberDTO.setDogAge(3);
        memberDTO.setRefreshToken("registerRefreshToken");
        snsMemberRepository.save(memberDTO);

        snsMemberRepository.deleteMember(memberDTO.getSnsId());

        boolean result = snsMemberRepository.findById(memberDTO.getSnsId()).isEmpty();
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void isMember(){
        boolean isFalse = snsMemberRepository.isMember("false");
        Assertions.assertThat(isFalse).isFalse();

        SnsMemberRegisterDTO memberDTO = new SnsMemberRegisterDTO();
        memberDTO.setSnsId("true");
        memberDTO.setNickname("registerNickname");
        memberDTO.setDogName("registerDogName");
        memberDTO.setDogAge(3);
        memberDTO.setRefreshToken("registerRefreshToken");
        snsMemberRepository.save(memberDTO);

        boolean isTrue = snsMemberRepository.isMember("true");

        Assertions.assertThat(isTrue).isTrue();
    }
}