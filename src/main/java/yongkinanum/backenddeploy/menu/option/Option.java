package yongkinanum.backenddeploy.menu.option;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import yongkinanum.backenddeploy.menu.Menu;

import javax.persistence.*;

@Entity
@Table(name = "option_tb",
        indexes = {
                @Index(name = "option_menu_idx", columnList = "menu_idx")
        })
@Getter
@ToString
@NoArgsConstructor
public class Option {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 128, nullable = false)
    private String optionName;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;
}
