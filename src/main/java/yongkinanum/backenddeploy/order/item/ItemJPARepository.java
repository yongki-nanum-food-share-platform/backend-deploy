package yongkinanum.backenddeploy.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemJPARepository extends JpaRepository<Item, Long> {
    @Query("select i from Item i where i.order.user.idx = :idx")
    List<Item> findAllItemByUserIdx(@Param("idx") Long idx);

    @Query("select i from Item i where i.order.idx = :idx")
    List<Item> findAllItemByOrderIdx(@Param("idx") Long idx);
}
