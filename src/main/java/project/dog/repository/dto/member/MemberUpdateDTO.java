package project.dog.repository.dto.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberUpdateDTO {

    private String password;
    private String nickname;
    private String dogName;
    private Integer dogAge;

    public MemberUpdateDTO() {
    }
}
