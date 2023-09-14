package yongkinanum.backenddeploy.post;

import lombok.*;
import yongkinanum.backenddeploy.menu.option.Option;
import yongkinanum.backenddeploy.shop.Shop;
import yongkinanum.backenddeploy.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post_tb",
        indexes = {
                @Index(name = "post_user_idx", columnList = "user_idx"),
                @Index(name = "post_shop_idx", columnList = "shop_idx")
        })
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 32, nullable = false)
    private String title;

    @Column(length = 512, nullable = false)
    private String content;

    @Column(length = 32, nullable = false)
    private String time;

    @Column(length = 32, nullable = false)
    private String place;

    @Column(nullable = false)
    private String people;

    @Column(nullable = false)
    private Date createAt;

    @Column(length = 1, nullable = false)
    private Character delete;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Shop shop;

    @Builder
    public Post(Long idx, String title, String content, String time, String place, String people, Date createAt, Character delete, User user, Shop shop) {
        this.idx = idx;
        this.title = title;
        this.content = content;
        this.time = time;
        this.place = place;
        this.people = people;
        this.createAt = createAt;
        this.delete = delete;
        this.user = user;
        this.shop = shop;
    }
}
