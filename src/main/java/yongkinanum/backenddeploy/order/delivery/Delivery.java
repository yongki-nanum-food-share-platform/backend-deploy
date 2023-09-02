package yongkinanum.backenddeploy.order.delivery;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import yongkinanum.backenddeploy.order.Order;
import yongkinanum.backenddeploy.user.User;

import javax.persistence.*;

@Entity
@Table(name = "delivery_tb",
        indexes = {
                @Index(name = "delivery_user_idx", columnList = "user_idx"),
                @Index(name = "delivery_order_idx", columnList = "order_idx")
        })
@Getter
@ToString
@NoArgsConstructor
public class Delivery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 256, nullable = false)
    private String address;

    @Column(length = 1, nullable = false)
    private Character status;

    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Delivery(Long idx, String address, Character status, Order order, User user) {
        this.idx = idx;
        this.address = address;
        this.status = status;
        this.order = order;
        this.user = user;
    }
}
