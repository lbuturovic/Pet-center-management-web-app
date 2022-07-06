package ba.unsa.etf.pnwt.petcenter.Repositories;

import ba.unsa.etf.pnwt.petcenter.Models.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center, Integer> {

    @Query(value = "SELECT * FROM center c, center_service cs WHERE  c.id = cs.center_id AND cs.service_id = :id", nativeQuery = true)
    public Iterable<Center> findCenterByService(@Param("id") Integer id);

    @Query(value = "SELECT * FROM center c WHERE user_id = :id", nativeQuery = true)
    public Iterable<Center> findCenterByUser(@Param("id") Integer id);
    List<Center> findByActive(boolean active);



}
