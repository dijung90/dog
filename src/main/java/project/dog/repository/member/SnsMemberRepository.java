package project.dog.repository.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import project.dog.domain.member.SnsMember;
import project.dog.repository.dto.member.SnsMemberRegisterDTO;
import project.dog.repository.dto.member.SnsMemberUpdateDTO;
import project.dog.repository.dto.member.SnsRefreshTokenUpdateDTO;
import project.dog.repository.mapper.member.SnsMemberMapper;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SnsMemberRepository {

    private final SnsMemberMapper mapper;

    public void save(SnsMemberRegisterDTO member){
        mapper.save(member);
    }

    public Optional<SnsMember> findById(String snsId){
        return mapper.findById(snsId);
    }

    public List<SnsMember> findAll(){
        return mapper.findAll();
    }

    public void updateMember(@Param("snsId") String snsId, @Param("dto") SnsMemberUpdateDTO snsMemberUpdateDTO){
        mapper.updateMember(snsId, snsMemberUpdateDTO);
    }

    public void updateRefreshToken(SnsRefreshTokenUpdateDTO refreshTokenUpdateDTO){
        mapper.updateRefreshToken(refreshTokenUpdateDTO);
    }

    public void deleteMember(String snsId){
        mapper.deleteMember(snsId);
    }

    public boolean isMember(String snsId){
        String findId = mapper.isMember(snsId);

        if(findId == null){
            return false;
        }
        return true;
    }
}
