package yongkinanum.backenddeploy.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopJPARepository extends JpaRepository<Shop, Long> {
    @Query("select s from Shop  s where s.shopName = :shopName")
    Shop findByShopName(@Param("shopName") String shopName);
}
