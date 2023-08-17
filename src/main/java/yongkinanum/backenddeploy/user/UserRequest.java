package yongkinanum.backenddeploy.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

class UserRequest {
    @Getter
    public static class RegistDTO {
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 영문과 한글만 가능합니다.")
        @Size(min = 2, max = 64, message = "길이는 2자 이상 64자 이하여아 합니다.")
        private String userName;

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문과 숫자만 사용 가능하며(대문자는 선택사항입니다.)")
        @Size(min = 8, max = 128, message = "길이는 8자 이상 128자 이하여야 합니다.")
        private String userId;

        @Setter
        @NotBlank
        @Size(min = 8, max = 20, message = "8에서 20자 이내여야 합니다.")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$",
                message = "영문 대소문자, 숫자, 특수문자가 하나씩은 포함되어야 합니다."
        )
        private String password;

        public User toEntity() {
            return User.builder()
                    .userName(userName)
                    .userId(userId)
                    .password(password)
                    .role(String.valueOf(Role.ROLE_USER))
                    .createAt(new Date())
                    .unregist('Y')
                    .build();
        }
    }

    @Getter
    public static class LoginDTO {
        @NotBlank
        private String userId;

        @NotBlank
        private String password;
    }
}
