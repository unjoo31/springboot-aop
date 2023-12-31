package shop.mtcoding.aopstudy.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class JoinInDto {
    @NotNull // NotNull : null 불가능
    private String username;

    @NotEmpty(message = "패스워드는 비어있을 수 없다") // NotEmpty : null, empty 불가능
    private String password;

    @NotEmpty
    @Size(min = 4, max = 10) // Size : 길이 제한
    private String email;
}
