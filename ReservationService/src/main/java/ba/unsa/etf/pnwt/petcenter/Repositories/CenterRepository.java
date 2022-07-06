package ba.unsa.etf.pnwt.petcenter.Repositories;

import ba.unsa.etf.pnwt.petcenter.Models.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CenterRepository extends JpaRepository<Center, Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM center  WHERE id_main =:id", nativeQuery = true)
    void deleteCenterById(@Param("id") Integer id);
    Center findById(int centerId);

}
