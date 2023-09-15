package yongkinanum.backenddeploy.notice;

import lombok.*;
import yongkinanum.backenddeploy.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notice_tb",
        indexes = {
                @Index(name = "notice_user_idx", columnList = "user_idx")
        })
@Getter
@ToString
@NoArgsConstructor
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 128, nullable = false)
    private String content;

    @Column(nullable = false)
    private Date createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Notice(Long idx, String content, Date createAt, User user) {
        this.idx = idx;
        this.content = content;
        this.createAt = createAt;
        this.user = user;
    }
}