package yongkinanum.backenddeploy.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJPARepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.userId = :userId")
    User findByUserId(@Param("userId") String userId);
}
