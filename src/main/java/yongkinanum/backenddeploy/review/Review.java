package yongkinanum.backenddeploy.review;

import lombok.*;
import yongkinanum.backenddeploy.order.Order;
import yongkinanum.backenddeploy.shop.Shop;
import yongkinanum.backenddeploy.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "review_tb",
        indexes = {
                @Index(name = "review_user_idx", columnList = "user_idx"),
                @Index(name = "review_shop_idx", columnList = "shop_idx"),
                @Index(name = "review_order_idx", columnList = "order_idx")
        })
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 256, nullable = false)
    private String content;

    @Column(nullable = false)
    private Float starPoint;

    @Column(nullable = false)
    private Date createAt;

    @Column(length = 1, nullable = false)
    private Character delete;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Builder
    public Review(Long idx, String content, Float starPoint, Date createAt, Character delete, User user, Shop shop, Order order) {
        this.idx = idx;
        this.content = content;
        this.starPoint = starPoint;
        this.createAt = createAt;
        this.delete = delete;
        this.user = user;
        this.shop = shop;
        this.order = order;
    }
}
