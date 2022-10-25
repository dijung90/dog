package project.dog.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dog.domain.Member;
import project.dog.repository.dto.member.MemberLoginDTO;
import project.dog.repository.dto.member.MemberRegisterDTO;
import project.dog.repository.member.MemberRepository;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;

    public boolean register(MemberRegisterDTO registerDTO){
        //가입여부 확인
        boolean isMember = repository.isMember(registerDTO.getId());

        log.info("register isMember = {}", isMember);

        if(!isMember){
            repository.save(registerDTO);
            return true;
        }
        return false;
    }

    public Optional<Member> findById(String id){
        return repository.findById(id);
    }

    public boolean login(MemberLoginDTO loginDTO){
        log.info("loginDTO = {}", loginDTO.toString());

        Member member = repository.findById(loginDTO.getId()).orElse(null);

        if(member == null){
            log.info("member null");
            return false;
        }else if(member.getPassword().equals(loginDTO.getPassword())){
            return true;
        }
        return false;
    }

}
