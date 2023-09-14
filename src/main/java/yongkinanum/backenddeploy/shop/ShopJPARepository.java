package yongkinanum.backenddeploy.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopJPARepository extends JpaRepository<Shop, Long> {
    @Query("select s from Shop s where s.shopName = :shopName")
    Shop findByShopName(@Param("shopName") String shopName);

    @Query("select s from Shop s where  s.brand.brandName like %:brandName%")
    List<Shop> findAllShopByBrandName(@Param("brandName") String brandName);
}
