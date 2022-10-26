package project.dog.repository.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.dog.domain.board.BoardTalk;
import project.dog.repository.dto.board.BoardSearchDTO;
import project.dog.repository.dto.board.talk.BoardTalkUpdateDTO;
import project.dog.repository.mapper.board.BoardTalkMapper;
import project.dog.repository.dto.board.talk.BoardTalkRegisterDTO;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardTalkRepository {

    private final BoardTalkMapper mapper;

    void save(BoardTalkRegisterDTO registerDTO){
        mapper.save(registerDTO);
    }

    Optional<BoardTalk> findByNo(int no){
       return mapper.findByNo(no);
    }

    List<BoardTalk> findAll(){
        return mapper.findAll();
    }

    void update(int no, BoardTalkUpdateDTO updateDTO){
        mapper.update(no, updateDTO);
    }

    void delete(int no) {
        mapper.delete(no);
    }

    List<BoardTalk> findByKeyword(BoardSearchDTO searchDTO){
        return mapper.findByKeyword(searchDTO);
    }
}
