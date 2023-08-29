package yongkinanum.backenddeploy.shop.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewJPARepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.shop.idx = :shopId")
    List<Review> findByShopId(@Param("shopId") Long shopId);
}
