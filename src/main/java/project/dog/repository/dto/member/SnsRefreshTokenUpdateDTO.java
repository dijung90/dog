package project.dog.repository.dto.member;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SnsRefreshTokenUpdateDTO {
    private String snsId;
    private String refreshToken;

    public SnsRefreshTokenUpdateDTO() {
    }
}
