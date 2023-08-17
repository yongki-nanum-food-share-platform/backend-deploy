package yongkinanum.backenddeploy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.core.error.exception.Exception409;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserJPARepository userJPARepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void regist(UserRequest.RegistDTO registDTO) {
        sameIdCheck(registDTO.getUserId());

        registDTO.setPassword(passwordEncoder.encode(registDTO.getPassword()));
        User user = registDTO.toEntity();

        userJPARepository.save(user);
    }

    private void sameIdCheck(String userId) {
        User findUser = userJPARepository.findByUserId(userId);

        if(findUser != null) {
            throw new Exception409("이미 존재하는 아이디입니다.");
        }
    }

    public void login() {

    }
}
