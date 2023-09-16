package yongkinanum.backenddeploy.core.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import yongkinanum.backenddeploy.user.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {
    private final User user;
    private Map<String, Object> attributes;

    //일반 로그인 생성자
    public CustomUserDetails(User user) {
        this.user = user;
    }

    //OAuth 로그인 생성자
    public CustomUserDetails(User user, Map<String, Object> attributes ) {
        this.user = user;
        this.attributes = attributes;
    }

    // Oauth2User 인터페이스 메서드
    // 외부 로그인 시 사용됨
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // UserDetails 인터페이스 구현 메서드
    // 해당 User의 권한을 리턴
    // SecurityFilterChain에서 권한을 체크할 때 사용
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user.getRole().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    // Oauth2User 인터페이스 구현 메서드
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
