package yongkinanum.backenddeploy.order.delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeliveryJPARepository extends JpaRepository<Delivery, Long> {
    @Query("select d from Delivery d where d.order.idx = :idx")
    Delivery findByOrderIdx(@Param("idx") Long idx);
}
