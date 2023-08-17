package yongkinanum.backenddeploy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.core.error.exception.Exception409;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserJPARepository userJPARepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void regist(UserRequest.RegistDTO registDTO) {
        Date date = new Date();

        if(check(registDTO.getUserId())) {
            throw new Exception409("계정이 중복됩니다.");
        }
        sameIdCheck(registDTO.getUserId());

        registDTO.setPassword(passwordEncoder.encode(registDTO.getPassword()));
        User user = registDTO.toEntity();

        userJPARepository.save(user);
    }

    public boolean check(String userId) {
    public void sameIdCheck(String userId) {
        User findUser = userJPARepository.findByUserId(userId);

        return findUser != null;
    }

    
}
