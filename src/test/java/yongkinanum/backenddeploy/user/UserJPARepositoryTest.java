package yongkinanum.backenddeploy.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class UserJPARepositoryTest {
    @Autowired
    UserJPARepository userJPARepository;

    @Test
    @DisplayName("유저 등록 테스트")
    void save_test() {
        //given
        User user = User.builder()
                .userName("MinseokGo")
                .userId("rhalstjr1999")
                .password("alstjr12")
                .build();

        //when
        User saveUser = userJPARepository.save(user);
        User findUser = userJPARepository.findById(user.getIdx()).orElseThrow(
                () -> new RuntimeException("찾기 실패")
        );

        //then
        assertThat(findUser).isSameAs(saveUser);
    }
}