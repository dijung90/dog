package project.dog.repository.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.dog.domain.member.Member;
import project.dog.repository.dto.member.MemberRegisterDTO;
import project.dog.repository.dto.member.MemberUpdateDTO;
import project.dog.repository.mapper.member.MemberMapper;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberRepository {

    private final MemberMapper mapper;

    public void save(MemberRegisterDTO member){
        mapper.save(member);
    }

    public Optional<Member> findById(String id) {
        return mapper.findById(id);
    }

    public List<Member> findAll(){
        return mapper.findAll();
    }

    public void updateMember(String id, MemberUpdateDTO memberUpdateDTO){
        mapper.updateMember(id, memberUpdateDTO);
    }

    public void deleteMember(String id){
        mapper.deleteMember(id);
    }

    public boolean isMember(String id){

        String findId = mapper.isMember(id);
        if(findId == null){
            return false;
        }
        return true;
    }

}
