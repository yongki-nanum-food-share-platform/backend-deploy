package yongkinanum.backenddeploy.user;

import lombok.*;
import yongkinanum.backenddeploy.core.error.exception.Exception404;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_tb")
@Getter
@ToString
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 64, nullable = false)
    private String userName;

    @Column(length = 128, nullable = false, unique = true)
    private String userId;

    @Column(length = 256, nullable = false)
    private String password;

    @Column(length = 32, nullable = false)
    private String role;

    @Column(nullable = false)
    private Date createAt;

    @Setter
    @Column(length = 1, nullable = false)
    private Character unregist;

    @Builder
    public User(Long idx, String userName, String userId, String password, String role, Date createAt, Character unregist) {
        this.idx = idx;
        this.userName = userName;
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.createAt = createAt;
        this.unregist = unregist;
    }

    public void update(String newNickname, String newPassword) {
        this.userName = newNickname;
        this.password = newPassword;
    }

    public void updateUserName(String newNickname) {
        this.userName = newNickname;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void findUserNullCheck(User user) {
        if(user == null) {
            throw new Exception404("해당 유저를 찾을 수 없습니다.");
        }
    }
}
