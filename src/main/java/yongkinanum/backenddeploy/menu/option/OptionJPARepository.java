package yongkinanum.backenddeploy.menu.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OptionJPARepository extends JpaRepository<Option, Long> {
    @Query("select o from Option o where o.idx = :optionId")
    Option findByOptionId(@Param("optionId") Long optionId);
}
