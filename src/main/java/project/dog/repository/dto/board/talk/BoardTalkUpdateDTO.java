package project.dog.repository.dto.board.talk;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class BoardTalkUpdateDTO {

    private String title;
    private String content;
    private int fileNo;

    public BoardTalkUpdateDTO() {
    }
}
