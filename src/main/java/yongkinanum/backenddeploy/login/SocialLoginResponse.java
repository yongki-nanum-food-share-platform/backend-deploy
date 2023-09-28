package yongkinanum.backenddeploy.login;

import lombok.Getter;
import lombok.Setter;

class SocialLoginResponse {
    @Getter
    @Setter
    public static class KakaoDTO {
        private String kakaoUri;
    }
}
