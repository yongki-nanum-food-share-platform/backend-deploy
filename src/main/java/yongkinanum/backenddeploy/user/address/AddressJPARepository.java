package yongkinanum.backenddeploy.user.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressJPARepository extends JpaRepository<Address, Long> {
    @Query("select a from Address a where a.user.idx = :idx")
    List<Address> findAllAddressByUserIdx(@Param("idx") Long idx);
}
