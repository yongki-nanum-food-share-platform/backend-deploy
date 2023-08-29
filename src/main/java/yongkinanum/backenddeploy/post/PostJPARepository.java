package yongkinanum.backenddeploy.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostJPARepository extends JpaRepository<Post, Long> {
    @Override
    @Query("select p from Post p where p.delete = 'N'")
    List<Post> findAll();

    @Query("select p from Post p where p.user.userId = :userId and p.delete = 'N'")
    List<Post> findByUserId(@Param("userId")String userId);
}
