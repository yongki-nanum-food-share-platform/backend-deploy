package yongkinanum.backenddeploy.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserKakaoProfile {
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private String email;
        private Profile profile;

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class Profile {
            private String nickname;
        }
    }
}