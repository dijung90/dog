package project.dog.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import project.dog.domain.SnsMember;
import project.dog.repository.dto.member.SnsMemberRegisterDTO;
import project.dog.repository.dto.member.SnsMemberUpdateDTO;
import project.dog.repository.dto.member.SnsRefreshTokenUpdateDTO;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SnsMemberMapper {

    void save(SnsMemberRegisterDTO member);

    Optional<SnsMember> findById(String snsId);

    List<SnsMember> findAll();

    void updateMember(@Param("snsId") String snsId, @Param("dto") SnsMemberUpdateDTO snsMemberUpdateDTO);

    void updateRefreshToken(SnsRefreshTokenUpdateDTO refreshTokenUpdateDTO);

    void deleteMember(String snsId);
}
