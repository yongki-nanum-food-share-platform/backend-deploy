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

    private String userName;

    @Column(unique = true)
    private String userId;

    private String password;

    private String role;

    private Date createAt;

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
