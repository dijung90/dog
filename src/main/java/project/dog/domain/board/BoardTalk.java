package project.dog.domain.board;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Setter @Getter @ToString @EqualsAndHashCode
public class BoardTalk {

    private int no;
    private String title;
    private String writer;
    private String content;
    private Timestamp regDate;
    private Timestamp updateDate;
    private int fileNo;

    public BoardTalk() {
    }
}
