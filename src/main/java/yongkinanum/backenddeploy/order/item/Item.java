package yongkinanum.backenddeploy.order.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import yongkinanum.backenddeploy.menu.option.Option;
import yongkinanum.backenddeploy.order.Order;
import yongkinanum.backenddeploy.shop.Shop;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "item_tb",
        indexes = {
                @Index(name = "item_order_idx", columnList = "order_idx"),
                @Index(name = "item_option_idx", columnList = "option_idx"),
                @Index(name = "item_shop_idx", columnList = "shop_idx")
        })
@Getter
@ToString
@NoArgsConstructor
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private Date createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY)
    private Shop shop;

    @Builder
    public Item(Long idx, int quantity, int price, Date createAt, Order order, Option option, Shop shop) {
        this.idx = idx;
        this.quantity = quantity;
        this.price = price;
        this.createAt = createAt;
        this.order = order;
        this.option = option;
        this.shop = shop;
    }
}
