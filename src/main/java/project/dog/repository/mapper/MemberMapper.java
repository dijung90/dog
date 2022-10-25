package project.dog.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import project.dog.domain.Member;
import project.dog.repository.dto.member.MemberRegisterDTO;
import project.dog.repository.dto.member.MemberUpdateDTO;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    void save(MemberRegisterDTO member);

    Optional<Member> findById(String id);

    List<Member> findAll();

    void updateMember(@Param("id") String id, @Param("dto") MemberUpdateDTO memberUpdateDTO);

    void deleteMember(String id);

    String isMember(String id);

}
