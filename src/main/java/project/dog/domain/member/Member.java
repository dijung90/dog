package project.dog.domain.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter @Setter @ToString
public class Member {

    private String id;
    private String password;
    private String nickname;
    private String dogName;
    private Integer dogAge;
    private Timestamp regDate;
    private Timestamp lastDate;

    public Member() {
    }
}
