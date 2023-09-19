package yongkinanum.backenddeploy.chat.message;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import yongkinanum.backenddeploy.chat.Chat;
import yongkinanum.backenddeploy.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message_tb",
        indexes = {
                @Index(name = "message_chat_idx", columnList = "chat_idx"),
                @Index(name = "message_user_idx", columnList = "user_idx")
        })
@Getter
@ToString
@NoArgsConstructor
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 512, nullable = false)
    private String content;

    @Column(nullable = false)
    private Date createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;

    @Builder
    public Message(Long idx, String content, Date createAt, User user, Chat chat) {
        this.idx = idx;
        this.content = content;
        this.createAt = createAt;
        this.user = user;
        this.chat = chat;
    }
}
