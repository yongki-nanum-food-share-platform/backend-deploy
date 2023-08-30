package yongkinanum.backenddeploy.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import yongkinanum.backenddeploy.menu.option.Option;
import yongkinanum.backenddeploy.shop.Shop;
import yongkinanum.backenddeploy.user.User;

import javax.persistence.*;

@Entity
@Table(name = "cart_tb",
        indexes = {
                @Index(name = "cart_option_idx", columnList = "option_idx"),
                @Index(name = "cart_shop_idx", columnList = "shop_idx"),
                @Index(name = "cart_user_idx", columnList = "user_idx")
        })
@Getter
@ToString
@NoArgsConstructor
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY)
    private Shop shop;

    @Builder
    public Cart(Long idx, int quantity, User user, Option option, Shop shop) {
        this.idx = idx;
        this.quantity = quantity;
        this.user = user;
        this.option = option;
        this.shop = shop;
    }

    public void update(int quantity) {
        this.quantity = quantity;
    }
}
