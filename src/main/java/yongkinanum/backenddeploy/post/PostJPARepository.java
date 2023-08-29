package yongkinanum.backenddeploy.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yongkinanum.backenddeploy.user.User;

import java.util.List;

public interface PostJPARepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.user.userId = :userId")
    List<Post> findByUserId(@Param("userId")String userId);
}
