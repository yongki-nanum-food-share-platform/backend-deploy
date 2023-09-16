package yongkinanum.backenddeploy.core.security;


import yongkinanum.backenddeploy.user.User;
import yongkinanum.backenddeploy.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// 로그인을 직접 서비스에서 할 예정이기 때문에, 사용은 안하겠지만
// 나중에 통합테스트시에 유용하게 사용할 수 있기 때문에 나두자.
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserJPARepository userJPARepository;

    //시큐리티 session -> Authentication -> UserDetailsService
    // 여기서 리턴 된 값(UserDetails)이 Authentication 안에 들어간다.(리턴될때 들어간다.)
    // 그리고 시큐리티 session 안에 Authentication 이 들어간다.
    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User findUser = userJPARepository.findByUserId(userId);

        if (findUser == null) {
            return null;
        } else {
            User user = findUser;
            return new CustomUserDetails(user);
        }
    }
}

