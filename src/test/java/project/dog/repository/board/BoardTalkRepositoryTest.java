package project.dog.repository.board;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.dog.domain.board.BoardTalk;
import project.dog.repository.dto.board.BoardSearchDTO;
import project.dog.repository.dto.board.talk.BoardTalkRegisterDTO;
import project.dog.repository.dto.board.talk.BoardTalkUpdateDTO;

import java.util.List;

@SpringBootTest
@Slf4j
//@Transactional
class BoardTalkRepositoryTest {

    @Autowired
    private BoardTalkRepository repository;

    @Test
    void save(){
        BoardTalkRegisterDTO registerDTO = new BoardTalkRegisterDTO();
        registerDTO.setTitle("saveTitle");
        registerDTO.setWriter("saveWriter");
        registerDTO.setContent("saveContent");
        registerDTO.setFileNo(1);

        repository.save(registerDTO);

        BoardTalk findBoard = repository.findByNo(1).orElse(null);
        Assertions.assertThat(findBoard.getTitle()).isEqualTo(registerDTO.getTitle());
        Assertions.assertThat(findBoard.getWriter()).isEqualTo(registerDTO.getWriter());
        Assertions.assertThat(findBoard.getContent()).isEqualTo(registerDTO.getContent());
        Assertions.assertThat(findBoard.getFileNo()).isEqualTo(registerDTO.getFileNo());
    }

    @Test
    void findAll(){
        BoardTalkRegisterDTO boardA = new BoardTalkRegisterDTO();
        boardA.setTitle("saveTitleA");
        boardA.setWriter("saveWriterA");
        boardA.setContent("saveContentA");
        boardA.setFileNo(1);

        BoardTalkRegisterDTO boardB = new BoardTalkRegisterDTO();
        boardB.setTitle("saveTitleB");
        boardB.setWriter("saveWriterB");
        boardB.setContent("saveContentB");
        boardB.setFileNo(3);

        repository.save(boardA);
        repository.save(boardB);

        List<BoardTalk> findAll = repository.findAll();

        BoardTalk findBoardA = repository.findByNo(1).orElse(null);
        BoardTalk findBoardB = repository.findByNo(2).orElse(null);
        Assertions.assertThat(findAll).contains(findBoardA, findBoardB);
    }

    @Test
    void update(){
        BoardTalkRegisterDTO registerDTO = new BoardTalkRegisterDTO();
        registerDTO.setTitle("saveTitle");
        registerDTO.setWriter("saveWriter");
        registerDTO.setContent("saveContent");
        registerDTO.setFileNo(1);

        BoardTalkUpdateDTO updateDTO = new BoardTalkUpdateDTO();
        updateDTO.setTitle("updateTitle");
        updateDTO.setContent("updateContent");
        updateDTO.setFileNo(3);

        repository.save(registerDTO);

        repository.update(1, updateDTO);

        BoardTalk findBoard = repository.findByNo(1).orElse(null);
        Assertions.assertThat(findBoard.getTitle()).isEqualTo(updateDTO.getTitle());
        Assertions.assertThat(findBoard.getContent()).isEqualTo(updateDTO.getContent());
        Assertions.assertThat(findBoard.getFileNo()).isEqualTo(updateDTO.getFileNo());
    }

    @Test
    void delete(){
        BoardTalkRegisterDTO registerDTO = new BoardTalkRegisterDTO();
        registerDTO.setTitle("saveTitle");
        registerDTO.setWriter("saveWriter");
        registerDTO.setContent("saveContent");
        registerDTO.setFileNo(1);

        repository.save(registerDTO);

        repository.delete(1);

        boolean result = repository.findByNo(1).isEmpty();
        Assertions.assertThat(result).isTrue();

    }

    @Test
    void searchKeyword(){
        BoardTalkRegisterDTO boardA = new BoardTalkRegisterDTO();
        boardA.setTitle("titleA");
        boardA.setWriter("writerA");
        boardA.setContent("contentA");
        boardA.setFileNo(1);

        BoardTalkRegisterDTO boardB = new BoardTalkRegisterDTO();
        boardB.setTitle("titleB");
        boardB.setWriter("writerB");
        boardB.setContent("contentB");
        boardB.setFileNo(3);

        BoardTalkRegisterDTO boardC = new BoardTalkRegisterDTO();
        boardC.setTitle("titleC");
        boardC.setWriter("writerC");
        boardC.setContent("contentC");
        boardC.setFileNo(2);

        BoardSearchDTO searchDTO = new BoardSearchDTO();
        searchDTO.setGroup("writer");
        searchDTO.setKeyword("A");

        repository.save(boardA);
        repository.save(boardB);
        repository.save(boardC);

        List<BoardTalk> findBoardWriter = repository.findByKeyword(searchDTO);
        Assertions.assertThat(findBoardWriter.size()).isEqualTo(1);

        searchDTO.setGroup("title");
        List<BoardTalk> findBoardTitle = repository.findByKeyword(searchDTO);
        Assertions.assertThat(findBoardTitle.size()).isEqualTo(1);

    }
}