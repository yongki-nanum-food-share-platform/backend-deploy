package yongkinanum.backenddeploy.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartJPARepository extends JpaRepository<Cart, Long> {
    @Query("select c from Cart c where c.user.idx = :idx")
    List<Cart> findAllByUserIdx(@Param("idx") Long idx);

    @Modifying
    @Query("delete from Cart c where c.user.idx = :idx")
    void deleteByUserIdx(@Param("idx")Long idx);
}
