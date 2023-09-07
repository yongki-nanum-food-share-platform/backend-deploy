package yongkinanum.backenddeploy.shop;

import lombok.*;
import yongkinanum.backenddeploy.shop.brand.Brand;
import yongkinanum.backenddeploy.user.User;

import javax.persistence.*;

@Entity
@Table(name = "shop_tb",
        indexes = {
                @Index(name = "shop_user_idx", columnList = "user_idx"),
                @Index(name = "shop_brand_idx", columnList = "brand_idx")
        })
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Shop {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 64, nullable = false)
    private String shopName;

    @Column(length = 128, nullable = false)
    private String shopAddress;

    @Column(length = 512)
    private String description;

    @Column(nullable = false)
    private Float starPoint;

    @Column(nullable = false)
    private Integer reviewCount;

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

    @Builder
    public Shop(Long idx, String shopName, String shopAddress, String description, Float starPoint, Integer reviewCount, Integer orderCount, Character unregist, int tip, User user, Brand brand) {
        this.idx = idx;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.description = description;
        this.starPoint = starPoint;
        this.reviewCount = reviewCount;
        this.orderCount = orderCount;
        this.unregist = unregist;
        this.tip = tip;
        this.user = user;
        this.brand = brand;
    }
}
