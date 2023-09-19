package yongkinanum.backenddeploy.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatJPARepository extends JpaRepository<Chat, Long> {
    @Query("select c from Chat c where c.idx = :idx")
    Chat findByChatIdx(@Param("idx") Long idx);

    @Query("select c from Chat c where c.post.idx = :idx")
    Chat findChatByPostIdx(@Param("idx") Long idx);
}
