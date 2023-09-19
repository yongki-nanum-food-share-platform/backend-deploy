package yongkinanum.backenddeploy.chat.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberJPARepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.user.idx = :idx")
    List<Member> findMemberByUserIdx(@Param("idx") Long idx);

    @Query("select m from Member m where m.chat.idx = :idx")
    List<Member> findMemberByChatIdx(@Param("idx") Long idx);
}
