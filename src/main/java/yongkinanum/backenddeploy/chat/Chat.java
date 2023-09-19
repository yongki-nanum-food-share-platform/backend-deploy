package yongkinanum.backenddeploy.chat;

import lombok.*;
import yongkinanum.backenddeploy.post.Post;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chat_tb",
        indexes = {
                @Index(name = "chat_post_idx", columnList = "post_idx")
})
@Getter
@ToString
@NoArgsConstructor
public class Chat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 32, nullable = false)
    private String title;

    @Column(nullable = false)
    private Date createAt;

    @Setter
    @Column(length = 1, nullable = false)
    private Character delete;

    @OneToOne(fetch = FetchType.LAZY)
    private Post post;

    @Builder
    public Chat(Long idx, String title, Date createAt, Character delete, Post post) {
        this.idx = idx;
        this.title = title;
        this.createAt = createAt;
        this.delete = delete;
        this.post = post;
    }
}
