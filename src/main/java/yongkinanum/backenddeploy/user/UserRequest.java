package yongkinanum.backenddeploy.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

class UserRequest {
    @Getter
    public static class RegistDTO {
        private String userName;
        private String userId;
        @Setter
        private String password;
        private String passwordCheck;

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
        private String userId;
        private String password;
    }
}
