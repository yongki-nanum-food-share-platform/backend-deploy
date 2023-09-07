package yongkinanum.backenddeploy.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewJPARepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.shop.idx = :idx and r.delete = 'N'")
    List<Review> findByShopId(@Param("idx") Long idx);

    @Query("select r from Review r where r.user.idx = :idx and r.delete = 'N'")
    List<Review> findByUserId(@Param("idx") Long idx);
}
