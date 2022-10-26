package project.dog.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dog.domain.member.SnsMember;
import project.dog.repository.dto.member.SnsMemberRegisterDTO;
import project.dog.repository.member.SnsMemberRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SnsMemberService {

    private final SnsMemberRepository repository;

    public boolean register(SnsMemberRegisterDTO registerDTO){
        boolean isMember = repository.isMember(registerDTO.getSnsId());

        if(!isMember){
            repository.save(registerDTO);
            return true;
        }
        return false;
    }

    public SnsMember findById(String snsId){
        SnsMember findMember = repository.findById(snsId).orElse(null);
        return findMember;
    }

    public boolean isMember(String snsId){
        boolean isMember = repository.isMember(snsId);
        if(isMember){
            return true;
        }
        return false;
    }
}
