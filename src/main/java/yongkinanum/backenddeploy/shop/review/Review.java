package yongkinanum.backenddeploy.shop.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
}
