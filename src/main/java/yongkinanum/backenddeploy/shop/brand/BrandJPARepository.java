package yongkinanum.backenddeploy.shop.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandJPARepository extends JpaRepository<Brand, Long> {
    @Query("select b from Brand b where b.brandName = :brandName")
    Brand findByBrandName(@Param("brandName") String brandName);
}
