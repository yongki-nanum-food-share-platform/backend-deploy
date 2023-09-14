package yongkinanum.backenddeploy.post.share;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShareJPARepository extends JpaRepository<Share, Long> {
    @Query("select s from Share s where s.post.idx = :idx")
    List<Share> findAllShareByPostIdx(@Param("idx") Long idx);
}
