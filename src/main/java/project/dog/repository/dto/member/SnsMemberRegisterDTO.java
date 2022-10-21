package project.dog.repository.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SnsMemberRegisterDTO {

    private String snsId;
    private String nickname;
    private String dogName;
    private Integer dogAge;
    private String refreshToken;

    public SnsMemberRegisterDTO() {
    }
}
