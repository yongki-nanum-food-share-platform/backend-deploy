package yongkinanum.backenddeploy.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.user.idx = :idx and o.cancel = 'N'")
    List<Order> findAllOrderByUserIdx(@Param("idx") Long idx);

    @Query("select o from Order o where o.idx = :idx and o.cancel = 'N'")
    Optional<Order> findById(@Param("idx") Long idx);
}
