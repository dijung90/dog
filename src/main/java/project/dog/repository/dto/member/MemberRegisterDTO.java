package project.dog.repository.dto.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberRegisterDTO {

    private String id;
    private String password;
    private String nickname;
    private String dogName;
    private Integer dogAge;

    public MemberRegisterDTO() {
    }

    public MemberRegisterDTO(String id, String password, String nickname, String dogName, Integer dogAge) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.dogName = dogName;
        this.dogAge = dogAge;
    }
}
