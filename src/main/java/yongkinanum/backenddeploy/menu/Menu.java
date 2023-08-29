package yongkinanum.backenddeploy.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import yongkinanum.backenddeploy.shop.brand.Brand;

import javax.persistence.*;

@Entity
@Table(name = "menu_tb",
        indexes = {
                @Index(name = "menu_brand_idx", columnList = "brand_idx")
        })
@Getter
@ToString
@NoArgsConstructor
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 64, nullable = false)
    private String menuName;

    @Column(length = 512, nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;
}
