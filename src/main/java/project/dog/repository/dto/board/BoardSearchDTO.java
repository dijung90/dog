package project.dog.repository.dto.board;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class BoardSearchDTO {
    private String group;
    private String keyword;

    public BoardSearchDTO() {
    }
}
