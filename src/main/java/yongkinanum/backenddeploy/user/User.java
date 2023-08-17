package yongkinanum.backenddeploy.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
}
