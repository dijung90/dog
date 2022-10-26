package project.dog.domain.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter @Setter @ToString
public class SnsMember {

    private String snsId;
    private String nickname;
    private String dogName;
    private Integer dogAge;
    private Timestamp regDate;
    private Timestamp lastDate;
    private String refreshToken;

    public SnsMember() {
    }
}
