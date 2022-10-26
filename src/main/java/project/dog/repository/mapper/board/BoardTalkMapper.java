package project.dog.repository.mapper.board;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import project.dog.domain.board.BoardTalk;
import project.dog.repository.dto.board.BoardSearchDTO;
import project.dog.repository.dto.board.talk.BoardTalkRegisterDTO;
import project.dog.repository.dto.board.talk.BoardTalkUpdateDTO;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardTalkMapper {

    void save(BoardTalkRegisterDTO registerDTO);

    void update(@Param("no") int no, @Param("param") BoardTalkUpdateDTO updateDTO);

    Optional<BoardTalk> findByNo(int no);

    List<BoardTalk> findAll();

    List<BoardTalk> findByKeyword(BoardSearchDTO searchDTO);

    void delete(int no);
}
