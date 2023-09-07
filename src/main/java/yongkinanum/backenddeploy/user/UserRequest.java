package yongkinanum.backenddeploy.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

class UserRequest {
    @Getter
    @Setter
    public static class RegistDTO {
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 영문과 한글만 가능합니다.")
        @Size(min = 2, max = 64, message = "길이는 2자 이상 64자 이하여아 합니다.")
        private String userName;

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문과 숫자만 사용 가능하며(대문자는 선택사항입니다.)")
        @Size(min = 8, max = 128, message = "길이는 8자 이상 128자 이하여야 합니다.")
        private String userId;

        @NotBlank
        @Size(min = 8, max = 20, message = "8에서 20자 이내여야 합니다.")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$",
                message = "영문 대소문자, 숫자, 특수문자가 하나씩은 포함되어야 합니다."
        )
        private String password;

        @NotBlank
        private String role;

        public User toEntity() {
            if(role.equals("가게사장님")) {
                return User.builder()
                        .userName(userName)
                        .userId(userId)
                        .password(password)
                        .role(Role.ROLE_SHOPPER.getDescription())
                        .createAt(new Date())
                        .unregist('N')
                        .build();
            } else if (role.equals("라이더")) {
                return User.builder()
                        .userName(userName)
                        .userId(userId)
                        .password(password)
                        .role(Role.ROLE_RIDER.getDescription())
                        .createAt(new Date())
                        .unregist('N')
                        .build();
            }

            return User.builder()
                    .userName(userName)
                    .userId(userId)
                    .password(password)
                    .role(Role.ROLE_USER.getDescription())
                    .createAt(new Date())
                    .unregist('N')
                    .build();
        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        @NotBlank
        private String userId;

        @NotBlank
        private String password;
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        private String newNickname;

        private String oldPassword;

        private String newPassword;
    }

    @Getter
    @Setter
    public static class AddAddressDTO {
        private String address;
    }
}
