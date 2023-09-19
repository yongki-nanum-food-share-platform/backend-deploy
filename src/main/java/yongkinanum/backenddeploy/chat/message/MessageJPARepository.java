package yongkinanum.backenddeploy.chat.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageJPARepository extends JpaRepository<Message, Long> {
    @Query("select m from Message m where m.chat.idx = :idx and m.chat.delete = 'N'")
    List<Message> findMessageByChatIdx(@Param("idx") Long idx);
}
