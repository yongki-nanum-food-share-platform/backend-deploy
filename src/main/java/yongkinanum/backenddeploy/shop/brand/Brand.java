package yongkinanum.backenddeploy.shop.brand;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "brand_tb")
@Getter
@ToString
@NoArgsConstructor
public class Brand {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 64, nullable = false)
    private String brandName;

    @Column(length = 512, nullable = false)
    private String image;
}
