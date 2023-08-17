package yongkinanum.backenddeploy.user;

import lombok.Getter;

public class UserRequest {
    @Getter
    public static class RegistDTO {
        private String userName;
        private String userId;
        private String password;
        private String passwordCheck;
    }

    @Getter
    public static class CheckDTO {
        private String userId;
    }
}
