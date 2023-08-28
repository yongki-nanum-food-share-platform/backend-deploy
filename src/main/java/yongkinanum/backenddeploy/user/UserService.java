package yongkinanum.backenddeploy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.core.error.exception.Exception400;
import yongkinanum.backenddeploy.core.error.exception.Exception409;
import yongkinanum.backenddeploy.core.security.JwtProvider;

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

    public String login(UserRequest.LoginDTO loginDTO) {
        User user = userJPARepository.findByUserId(loginDTO.getUserId());
        checkUnregistUser(findUser);

        if(user == null) {
            throw new Exception400("로그인에 실패하였습니다.");
        }

        if(!user.getUserId().equals(loginDTO.getUserId()) || !passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new Exception400("로그인에 실패하였습니다.");
        }

        return JwtProvider.create(user);
        checkUnregistUser(findUser);
        checkUnregistUser(findUser);
    private void checkUnregistUser(User user) {
        if(user.getUnregist() == 'N') {
            throw new Exception400("해당 유저의 정보를 찾을 수 없습니다.");
        }
    }
}
