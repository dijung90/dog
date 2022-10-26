package project.dog.repository.dto.board.talk;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class BoardTalkRegisterDTO {

    private String title;
    private String writer;
    private String content;
    private int fileNo;

    public BoardTalkRegisterDTO() {
    }
}
