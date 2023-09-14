package yongkinanum.backenddeploy.post.share;

import lombok.*;
import yongkinanum.backenddeploy.menu.option.Option;
import yongkinanum.backenddeploy.post.Post;

import javax.persistence.*;

@Entity
@Table(name = "share_tb",
        indexes = {
                @Index(name = "share_post_idx", columnList = "post_idx"),
                @Index(name = "share_option_idx", columnList = "option_idx")
        })
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Share {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    @Builder
    public Share(Long idx, int quantity, Post post, Option option) {
        this.idx = idx;
        this.quantity = quantity;
        this.post = post;
        this.option = option;
    }
}
