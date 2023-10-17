package yongkinanum.backenddeploy.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import yongkinanum.backenddeploy.core.security.JwtProvider;
import yongkinanum.backenddeploy.core.utils.ApiUtils;
import yongkinanum.backenddeploy.user.Role;
import yongkinanum.backenddeploy.user.User;
import yongkinanum.backenddeploy.user.UserService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class SocialLoginController {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String naverRedirectUri;

    private final UserService userService;


    @GetMapping("/login/kakao")
    public ResponseEntity<?> kakaoLogin() {
        String url = String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code", kakaoClientId, kakaoRedirectUri);
        SocialLoginResponse.KakaoDTO kakaoDTO = new SocialLoginResponse.KakaoDTO();
        kakaoDTO.setKakaoUri(url);

        return ResponseEntity.ok().body(ApiUtils.success(kakaoDTO));
    }

    @GetMapping("/login/naver")
    public ResponseEntity<?> naverLogin() {
        String url = String.format("https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&state=test", naverClientId, naverRedirectUri);
        System.out.println("url = " + url);
        SocialLoginResponse.NaverDTO naverDTO = new SocialLoginResponse.NaverDTO();
        naverDTO.setNaverUri(url);

        return ResponseEntity.ok().body(ApiUtils.success(naverDTO));
    }

    @PostMapping("/login/naver/check")
    public @ResponseBody ResponseEntity<?> naverCallback(@RequestBody String code){
        System.out.println("code = " + code);
        // access_token 받아오기
        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","text/html;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverClientId);
        params.add("state", "test");
        params.add("code", code);
        params.add("client_secret", "KO0Zw6fyUD");

        // 요청하기 위해 헤더(Header)와 데이터(Body)를 합친다.
        // kakaoTokenRequest는 데이터(Body)와 헤더(Header)를 Entity가 된다.
        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(params, headers);

        System.out.println("11111 = " + 22222);


        // POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
        ResponseEntity<String> response = rt.exchange(
                "https://nid.naver.com/oauth2.0/token", // https://{요청할 서버 주소}
                HttpMethod.POST, // 요청할 방식
                naverTokenRequest, // 요청할 때 보낼 데이터
                String.class // 요청 시 반환되는 데이터 타입
        );

        System.out.println("11111 = " + 11111);


        ObjectMapper om = new ObjectMapper();
        NaverOAuthToken naverOAuthToken = null;

        try{
            naverOAuthToken = om.readValue(response.getBody(),NaverOAuthToken.class);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization","Bearer "+ Objects.requireNonNull(naverOAuthToken).getAccess_token());
        headers.add("Content-type","text/json;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(headers2);


        // POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://openapi.naver.com/v1/nid/me", // https://{요청할 서버 주소}
                HttpMethod.GET, // 요청할 방식
                naverProfileRequest, // 요청할 때 보낼 데이터
                String.class // 요청 시 반환되는 데이터 타입
        );

        ObjectMapper om2 = new ObjectMapper();
        UserNaverProfile userNaverProfile = null;

        try{
            userNaverProfile = om2.readValue(response2.getBody(),UserNaverProfile.class);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        User user = User.builder()
                .userId(userNaverProfile.getEmail())
                .userName(userNaverProfile.getNickname())
                .role(Role.ROLE_USER.getDescription())
                .createAt(new Date())
                .password("naver")
                .unregist('Y')
                .build();

        String jwt = userService.socialLogin(Objects.requireNonNull(user));

        return ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body(ApiUtils.success(null));
    }

    @PostMapping("/login/kakao/check")
    public @ResponseBody ResponseEntity<?> kakaoCallback(@RequestBody String code){
        // 인가 코드 받아오기
        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);
        params.add("client_secret", "zLl1gqgJnOSTUsWJBuVad86Ds1yIZd4U");

        // 요청하기 위해 헤더(Header)와 데이터(Body)를 합친다.
        // kakaoTokenRequest는 데이터(Body)와 헤더(Header)를 Entity가 된다.
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token", // https://{요청할 서버 주소}
                HttpMethod.POST, // 요청할 방식
                kakaoTokenRequest, // 요청할 때 보낼 데이터
                String.class // 요청 시 반환되는 데이터 타입
        );

        ObjectMapper om = new ObjectMapper();
        KakaoOAuthToken kakaoOAuthToken = null;

        try{
            kakaoOAuthToken = om.readValue(response.getBody(),KakaoOAuthToken.class);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        // access_token 받아오기
        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization","Bearer "+ Objects.requireNonNull(kakaoOAuthToken).getAccess_token());
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);


        // POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me", // https://{요청할 서버 주소}
                HttpMethod.POST, // 요청할 방식
                kakaoProfileRequest, // 요청할 때 보낼 데이터
                String.class // 요청 시 반환되는 데이터 타입
        );

        ObjectMapper om2 = new ObjectMapper();
        UserKakaoProfile userKakaoProfile = null;

        try{
            userKakaoProfile = om2.readValue(response2.getBody(),UserKakaoProfile.class);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        User user = User.builder()
                .userId(String.valueOf(userKakaoProfile.getId()))
                .password("kakao")
                .userName("kakao" + userKakaoProfile.getId())
                .role(Role.ROLE_USER.getDescription())
                .createAt(new Date())
                .unregist('Y')
                .build();

        String jwt = userService.socialLogin(Objects.requireNonNull(user));

        return ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body(ApiUtils.success(null));
    }
}
