package yongkinanum.backenddeploy.login;

import lombok.Getter;
import lombok.Setter;

class SocialLoginResponse {
    @Getter
    @Setter
    public static class KakaoDTO {
        private String kakaoUri;
    }

    @Getter
    @Setter
    public static class NaverDTO {
        private String naverUri;
    }

    @Getter
    @Setter
    public static class GoogleDTO {
        private String googleUri;
    }
}
