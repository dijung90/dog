package project.dog.repository.dto.member;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SnsMemberUpdateDTO {
    private String nickname;
    private String dogName;
    private Integer dogAge;

    public SnsMemberUpdateDTO() {
    }
}
