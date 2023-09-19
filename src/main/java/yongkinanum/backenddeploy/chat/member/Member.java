package yongkinanum.backenddeploy.chat.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import yongkinanum.backenddeploy.chat.Chat;
import yongkinanum.backenddeploy.user.User;

import javax.persistence.*;

@Entity
@Table(name = "member_tb",
        indexes = {
                @Index(name = "member_chat_idx", columnList = "chat_idx"),
                @Index(name = "member_user_idx", columnList = "user_idx")
        })
@Getter
@ToString
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;

    @Builder
    public Member(Long idx, User user, Chat chat) {
        this.idx = idx;
        this.user = user;
        this.chat = chat;
    }
}
