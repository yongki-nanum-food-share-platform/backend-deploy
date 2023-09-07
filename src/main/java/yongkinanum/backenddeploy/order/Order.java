package yongkinanum.backenddeploy.order;

import lombok.*;
import yongkinanum.backenddeploy.user.User;

import javax.persistence.*;

@Entity
@Table(name = "order_tb",
        indexes = {
                @Index(name = "order_user_idx", columnList = "user_idx")
        })
@Getter
@ToString
@NoArgsConstructor
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 128, nullable = false)
    private String orderName;

    @Setter
    @Column(length = 1, nullable = false)
    private Character cancel;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Order(Long idx, String orderName, Character cancel, User user) {
        this.idx = idx;
        this.orderName = orderName;
        this.cancel = cancel;
        this.user = user;
    }
}