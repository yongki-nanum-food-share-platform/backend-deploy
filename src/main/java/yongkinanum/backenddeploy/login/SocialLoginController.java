package yongkinanum.backenddeploy.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yongkinanum.backenddeploy.user.UserJPARepository;

@Controller
@RequiredArgsConstructor
public class SocialLoginController {
    private final UserJPARepository userJPARepository;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/login/kakao")
    public String showLoginPage(Model model) {
        String url = "https://kauth.kakao.com/oauth/authorize?client_id=e3743c41d0df1be9ef7bdc6790434cde&redirect_uri=http://localhost:8080/login/oauth2/code/kakao";
        System.out.println("login 컨트롤러 접근");
        return url;
    }

    @GetMapping("/login")
    public String home() {
        return "test/login";
    }

    @PostMapping("/login/oauth2/code/kakao")
    public String receive(@RequestParam String code) {
        System.out.println(code);

        return "success";
    }
}
