package project.dog.repository.dto.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class MemberLoginDTO {

    private String id;
    private String password;

    public MemberLoginDTO() {
    }
}
