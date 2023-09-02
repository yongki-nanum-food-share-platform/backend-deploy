package yongkinanum.backenddeploy.user.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import yongkinanum.backenddeploy.user.User;

import javax.persistence.*;

@Entity
@Table(name = "address_tb",
        indexes = {
                @Index(name = "address_user_idx", columnList = "user_idx")
        })
@Getter
@ToString
@NoArgsConstructor
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 256, nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
