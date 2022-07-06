package ba.unsa.etf.pnwt.petcenter.Repository;

import ba.unsa.etf.pnwt.petcenter.Model.Center;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CenterRepository extends CrudRepository<Center, Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM center  WHERE id =:id", nativeQuery = true)
    void deleteCenterById(@Param("id") Integer id);
    List<Center> findAllByActive(boolean b);
}
