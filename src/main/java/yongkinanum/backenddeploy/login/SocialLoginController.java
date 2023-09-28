package yongkinanum.backenddeploy.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yongkinanum.backenddeploy.core.utils.ApiUtils;
import yongkinanum.backenddeploy.user.UserJPARepository;

@RequiredArgsConstructor
@RestController
public class SocialLoginController {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;


    @GetMapping("/login/kakao")
    public ResponseEntity<?> kakaoLogin() {
        String url = String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code", clientId, redirectUri);
        SocialLoginResponse.KakaoDTO kakaoDTO = new SocialLoginResponse.KakaoDTO();
        kakaoDTO.setKakaoUri(url);

        return ResponseEntity.ok().body(ApiUtils.success(kakaoDTO));
    }

    @GetMapping("/login/kakao/check")
    public String receive(@RequestParam String code) {
        System.out.println(code);

        return "success";
    }
}
