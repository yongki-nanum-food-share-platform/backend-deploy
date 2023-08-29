package yongkinanum.backenddeploy.shop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import yongkinanum.backenddeploy.shop.brand.Brand;
import yongkinanum.backenddeploy.user.User;

import javax.persistence.*;

@Entity
@Table(name = "shop_tb",
        indexes = {
                @Index(name = "shop_user_idx", columnList = "user_idx"),
                @Index(name = "shop_brand_idx", columnList = "brand_idx")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_shop_user_brand", columnNames = {"brand_idx"})
        })
@Getter
@ToString
@NoArgsConstructor
public class Shop {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 64, nullable = false)
    private String shopName;

    @Column(length = 128, nullable = false)
    private String shopAddress;

    @Column(length = 512, nullable = false)
    private String image;

    @Column(nullable = false)
    private Float starPoint;

    @Column(nullable = false)
    private Integer orderCount;

    @Column(length = 1, nullable = false)
    private Character unregist;

    @Column(nullable = false)
    private int tip;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Brand brand;
}
